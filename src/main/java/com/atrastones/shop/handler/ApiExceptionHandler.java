package com.atrastones.shop.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex);
        return buildErrorResponse("INTERNAL.SERVER.ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ApiErrorResponse> handleTokenExceptions(JWTVerificationException ex) {
        log.error("Token error", ex);
        return buildErrorResponse(
                (ex instanceof TokenExpiredException) ? "TOKEN.IS.EXPIRED" : "INVALID.TOKEN"
                , HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabase(DataAccessException ex) {
        log.error("Database access error", ex);
        return buildErrorResponse("DATABASE.ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> Optional.ofNullable(fe.getDefaultMessage())
                                .orElse(resolveMessage("VALIDATION.DEFAULT")),
                        (existing, _) -> existing
                ));

        List<String> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList();

        Map<String, Object> details = new ConcurrentHashMap<>();
        if (!fieldErrors.isEmpty()) {
            details.put("fieldErrors", fieldErrors);
        }
        if (!globalErrors.isEmpty()) {
            details.put("globalErrors", globalErrors);
        }

        return buildErrorResponse("VALIDATION.ERROR", HttpStatus.BAD_REQUEST, details);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthentication(AuthenticationException ex) {
        log.debug("Authentication failed: {}", ex.getMessage());
        return buildErrorResponse("FORBIDDEN.AUTHENTICATION", HttpStatus.FORBIDDEN, null);
    }

    // -------------------------------------- Helper methods --------------------------------------

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(String messageKey, HttpStatus status, Map<String, Object> details) {
        return buildErrorResponse(status, resolveMessage(messageKey), details);
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message, Map<String, Object> details) {
        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(
                        message,
                        details,
                        LocalDateTime.now()
                ));
    }

    private String resolveMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(key, null, locale);
        } catch (Exception e) {
            log.warn("Missing i18n message for key: {}", key);
            return key;
        }
    }

    // -------------------------------------- Error Response DTO --------------------------------------

    public record ApiErrorResponse(
            String message,
            Map<String, Object> details,
            LocalDateTime timestamp
    ) {
    }
}

package com.atrastones.infrastructure.error;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestControllerAdvice
class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageSourceAccessor messageSourceAccessor;

    public GlobalExceptionHandler(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex);
        return buildErrorResponse("internal.server.error", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(DataAccessException.class)
    ResponseEntity<ApiErrorResponse> handleDatabase(DataAccessException ex) {
        log.error("Database access error", ex);
        return buildErrorResponse("DATABASE.ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(InvalidResourceException.class)
    ResponseEntity<ApiErrorResponse> handleServiceLogic(InvalidResourceException ex) {
        return buildErrorResponse(ex.messageKey(), HttpStatus.UNPROCESSABLE_CONTENT, null);
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ApiErrorResponse> handleAuthentication(AuthenticationException ex) {
        return buildErrorResponse("authentication.invalid", HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorResponse> handleConstraint(MethodArgumentNotValidException ex) {
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

        if (!fieldErrors.isEmpty())
            details.put("fieldErrors", fieldErrors);

        if (!globalErrors.isEmpty())
            details.put("globalErrors", globalErrors);

        return buildErrorResponse("invalid.method.params", HttpStatus.BAD_REQUEST, details);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ApiErrorResponse> handleEntityNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.messageKey(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    ResponseEntity<ApiErrorResponse> handleAuthorization(AuthorizationDeniedException ex, HttpServletRequest request) {
        log.debug("Authorization failed: {}", ex.getMessage());
        return buildErrorResponse((request.getAttribute("isExpired") != null) ? "token.is.expired" : "authorization.invalid", HttpStatus.FORBIDDEN, null);
    }

    // ======================================== HELPERS ========================================

    private String resolveMessage(String key) {
        try {
            return messageSourceAccessor.getMessage(key);
        } catch (Exception e) {
            log.warn("Missing i18n message for key: {}", key);
            return key;
        }
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message, Object details) {
        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(
                        message,
                        details,
                        LocalDateTime.now()
                ));
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(String messageKey, HttpStatus status, Object details) {
        return buildErrorResponse(status, resolveMessage(messageKey), details);
    }

    // =================================== TYPES ===================================

    private record ApiErrorResponse(
            String message,
            Object details,
            LocalDateTime timestamp
    ) {
    }

}

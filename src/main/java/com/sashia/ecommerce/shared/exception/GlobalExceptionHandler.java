package com.sashia.ecommerce.shared.exception;

import com.sashia.ecommerce.shared.web.ApiResponse;
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
    ResponseEntity<ApiResponse> handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex);
        return buildErrorResponse("internal.server.error", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(DataAccessException.class)
    ResponseEntity<ApiResponse> handleDatabase(DataAccessException ex) {
        log.error("Database access error", ex);
        return buildErrorResponse("DATABASE.ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ApiResponse> handleAuthentication(AuthenticationException ex) {
        return buildErrorResponse("authentication.invalid", HttpStatus.UNAUTHORIZED, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleConstraint(MethodArgumentNotValidException ex) {
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
    ResponseEntity<ApiResponse> handleEntityNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.messageKey(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(BusinessRuleException.class)
    ResponseEntity<ApiResponse> handleBusinessException(BusinessRuleException ex) {
        return buildErrorResponse(ex.messageKey(), HttpStatus.UNPROCESSABLE_CONTENT, null);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    ResponseEntity<ApiResponse> handleAuthorization(AuthorizationDeniedException ex) {
        log.debug("Authorization failed: {}", ex.getMessage());
        return buildErrorResponse("authorization.invalid", HttpStatus.FORBIDDEN, null);
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

    private ResponseEntity<ApiResponse> buildErrorResponse(HttpStatus status, String message, Object details) {
        return ResponseEntity.status(status)
                .body(new ApiResponse(
                        message,
                        details,
                        0
                ));
    }

    private ResponseEntity<ApiResponse> buildErrorResponse(String messageKey, HttpStatus status, Object details) {
        return buildErrorResponse(status, resolveMessage(messageKey), details);
    }

}
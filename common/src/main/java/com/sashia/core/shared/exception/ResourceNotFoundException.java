package com.sashia.core.shared.exception;

public class ResourceNotFoundException extends APIException {

    public ResourceNotFoundException(String messageKey) {
        super(messageKey, ErrorCategory.NOT_FOUND);
    }

}

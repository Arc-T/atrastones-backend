package com.sashia.core.shared.exception;

public class InvalidResourceException extends APIException {

    public InvalidResourceException(String messageKey) {
        super(messageKey, ErrorCategory.NOT_FOUND);
    }

}

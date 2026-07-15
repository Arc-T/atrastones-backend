package com.sashia.ecommerce.shared.exception;

public class InvalidResourceException extends BaseException {

    public InvalidResourceException(String messageKey) {
        super(messageKey);
    }

}

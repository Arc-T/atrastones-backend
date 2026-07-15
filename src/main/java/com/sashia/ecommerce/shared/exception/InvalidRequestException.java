package com.sashia.ecommerce.shared.exception;

public class InvalidRequestException extends BaseException {

    public InvalidRequestException(String messageKey) {
        super(messageKey);
    }

}

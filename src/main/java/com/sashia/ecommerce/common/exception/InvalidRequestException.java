package com.sashia.ecommerce.common.exception;

public class InvalidRequestException extends BaseException {

    public InvalidRequestException(String messageKey) {
        super(messageKey);
    }

}

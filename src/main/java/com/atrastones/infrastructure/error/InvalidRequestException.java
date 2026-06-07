package com.atrastones.infrastructure.error;

public class InvalidRequestException extends BaseException {

    public InvalidRequestException(String messageKey) {
        super(messageKey);
    }

}

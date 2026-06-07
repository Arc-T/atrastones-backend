package com.atrastones.infrastructure.error;

public class InvalidResourceException extends BaseException {

    public InvalidResourceException(String messageKey) {
        super(messageKey);
    }

}

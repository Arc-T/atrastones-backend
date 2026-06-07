package com.atrastones.infrastructure.error;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String messageKey) {
        super(messageKey);
    }

}

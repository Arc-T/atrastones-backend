package com.sashia.ecommerce.common.exception;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String messageKey) {
        super(messageKey);
    }

}

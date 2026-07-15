package com.sashia.ecommerce.shared.exception;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String messageKey) {
        super(messageKey);
    }

}

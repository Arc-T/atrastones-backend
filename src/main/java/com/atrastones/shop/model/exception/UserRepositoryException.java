package com.atrastones.shop.model.exception;

import com.atrastones.shop.exception.GenericException;

public class UserRepositoryException extends GenericException {

    public UserRepositoryException(String message, int code) {
        super(message, code);
    }
}

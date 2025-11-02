package com.atrastones.shop.model.exception;

import com.atrastones.shop.exception.GenericException;

public class DbInsertException extends GenericException {

    public DbInsertException(String message, int code) {
        super(message, code);
    }
}

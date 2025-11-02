package com.atrastones.shop.exception;

import lombok.Getter;

@Getter
public abstract class GenericException extends RuntimeException {

    private final int code;

    public GenericException(String message, int code) {
        super(message);
        this.code = code;
    }
}

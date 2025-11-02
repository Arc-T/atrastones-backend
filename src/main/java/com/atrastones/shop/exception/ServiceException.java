package com.atrastones.shop.exception;

public class ServiceException extends GenericException{

    public ServiceException(String message, int code) {
        super(message, code);
    }
}

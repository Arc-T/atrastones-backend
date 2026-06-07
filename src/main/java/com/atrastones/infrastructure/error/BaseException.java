package com.atrastones.infrastructure.error;

public abstract class BaseException extends RuntimeException {

    private final String messageKey;
    private final Object[] messageArguments;

    public BaseException(String messageKey) {
        super();
        this.messageKey = messageKey;
        this.messageArguments = null;
    }

    public BaseException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
        this.messageArguments = null;
    }

    public BaseException(String messageKey, Object[] messageArguments) {
        super();
        this.messageKey = messageKey;
        this.messageArguments = messageArguments;
    }

    public String messageKey() {
        return messageKey;
    }

    public Object[] messageArguments() {
        return messageArguments;
    }

}

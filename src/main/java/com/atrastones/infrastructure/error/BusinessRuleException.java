package com.atrastones.infrastructure.error;

public class BusinessRuleException extends BaseException {

    public BusinessRuleException(String messageKey) {
        super(messageKey);
    }

}

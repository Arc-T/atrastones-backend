package com.sashia.core.shared.exception;

public class BusinessRuleException extends APIException {

    public BusinessRuleException(String messageKey) {
        super(messageKey, ErrorCategory.BUSINESS_RULE);
    }

}

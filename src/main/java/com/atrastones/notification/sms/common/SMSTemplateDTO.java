package com.atrastones.notification.sms.common;

import com.atrastones.notification.common.SMSType;
import com.atrastones.notification.sms.SMSTemplate;

import java.time.LocalDateTime;

public record SMSTemplateDTO(
        Long id,
        String name,
        String message,
        SMSType type,
        Short params,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static SMSTemplateDTO toDTO(SMSTemplate smsTemplate) {
        return new SMSTemplateDTO(
                smsTemplate.id(),
                smsTemplate.name(),
                smsTemplate.message(),
                smsTemplate.type(),
                smsTemplate.params(),
                smsTemplate.createdAt(),
                smsTemplate.updatedAt()
        );
    }

}

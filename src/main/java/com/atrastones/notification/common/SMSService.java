package com.atrastones.notification.common;

import com.atrastones.notification.sms.common.SMSTemplateDTO;

import java.util.Optional;

public interface SMSService {

    // ****************************** CRUD ******************************

    Long create(SMSDTO smsDTO);

    Optional<SMSDTO> get(Long id);

    Optional<SMSDTO> getPhoneLatestSmsMessage(String phone);

    Optional<SMSTemplateDTO> getTemplateByType(SMSType type);

    // ****************************** OPERATIONS ******************************

    void sendSms(SMSEventDTO event);

}
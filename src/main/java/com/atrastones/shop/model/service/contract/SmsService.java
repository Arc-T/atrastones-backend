package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.SmsDTO;
import com.atrastones.shop.type.SmsType;

import java.util.Optional;

public interface SmsService {

    // ****************************** CRUD ******************************

    Optional<SmsDTO> get(Long id);

    String getMessageTemplateByType(SmsType type);

    Optional<SmsDTO> getPhoneLatestSmsMessage(String phone);

    // ****************************** OPERATIONS ******************************

    int getOrCreateTtl(String phone);

    void sendSms(String phone, String message);

}
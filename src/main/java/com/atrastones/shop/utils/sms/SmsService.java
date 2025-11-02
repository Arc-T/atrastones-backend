package com.atrastones.shop.utils.sms;

public interface SmsService {

    void send(String phoneNumber, String message);

    SmsProvider getSMSProvider();
}

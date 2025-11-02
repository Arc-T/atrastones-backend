package com.atrastones.shop.utils.sms;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SmsFactory {

    private final Map<SmsProvider, SmsService> providers = new HashMap<>();

    public SmsFactory(List<SmsService> smsServices) {
        for (SmsService smsService : smsServices) {
            providers.put(smsService.getSMSProvider(), smsService);
        }
    }

    public SmsService getSMSService(SmsProvider provider) {
        SmsService service = providers.get(provider);
        if (service == null) {
            throw new IllegalArgumentException("Unknown provider: " + provider);
        }
        return service;
    }
}
package com.atrastones.shop.utils.sms;

public abstract class Sms implements SmsService {

    @Override
    public void send(String to, String message) {
        // 1. Call provider-specific send
        String result = doSend(to, message);

        // 2. Shared logic: insert SMS result
        saveResult(to, message, result);
    }
    // Provider-specific logic (each subclass implements this)
    protected abstract String doSend(String to, String message);

    // Common logic
    protected void saveResult(String to, String message, String result) {
        System.out.printf("Saving SMS log: to=%s, msg=%s, result=%s%n", to, message, result);
    }
}


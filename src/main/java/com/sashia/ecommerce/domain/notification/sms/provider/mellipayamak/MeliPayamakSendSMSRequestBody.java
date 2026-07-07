package com.sashia.ecommerce.domain.notification.sms.provider.mellipayamak;

public record MeliPayamakSendSMSRequestBody(
        String username,
        String password,
        String from,
        String phone,
        String message,
        boolean isFlash
) {
}

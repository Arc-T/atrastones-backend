package com.sashia.ecommerce.domain.notification.sms.provider.mellipayamak;

public record MeliPayamakSendSMSResponseBody(
        String Value,
        Long RetStatus,
        String StrRetStatus
) {
}

package com.atrastones.notification.sms.provider.mellipayamak;

public record MeliPayamakSendSMSResponseBody(
        String Value,
        Long RetStatus,
        String StrRetStatus
) {
}

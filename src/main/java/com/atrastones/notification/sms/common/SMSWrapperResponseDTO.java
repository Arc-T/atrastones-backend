package com.atrastones.notification.sms.common;

public record SMSWrapperResponseDTO(
        Long statusId,
        String result,
        String description
) {
}
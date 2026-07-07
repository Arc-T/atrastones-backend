package com.sashia.ecommerce.domain.notification.sms.common;

public record SMSWrapperResponseDTO(
        Long statusId,
        String result,
        String description
) {
}
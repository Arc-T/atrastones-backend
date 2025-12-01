package com.atrastones.shop.dto;

import java.time.LocalDateTime;

public record SmsDTO(
        Long id,
        String phone,
        Long statusId,
        Long templateId,
        String text,
        String response,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

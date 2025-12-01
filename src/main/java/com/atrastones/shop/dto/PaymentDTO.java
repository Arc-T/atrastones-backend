package com.atrastones.shop.dto;

import com.atrastones.shop.type.PaymentMethod;
import com.atrastones.shop.type.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentDTO(
        Long id,
        Long userId,
        Long orderId,
        PaymentMethod paymentMethod,
        Long amount,
        PaymentStatus status,
        String authority,
        String referenceId,
        String feeType,
        String fee,
        String description,
        LocalDateTime createdAt
) {
}

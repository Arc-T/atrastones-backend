package com.sashia.ecommerce.promotion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountEditDTO(
        String name,
        String discountableType,
        Long typeId,
        Long scopeId,
        BigDecimal amount,
        LocalDateTime startDate,
        LocalDateTime expireDate,
        Integer maxUses,
        Boolean isActive,
        String description
) {
}

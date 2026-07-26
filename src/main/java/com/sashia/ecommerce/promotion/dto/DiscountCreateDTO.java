package com.sashia.ecommerce.promotion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DiscountCreateDTO(
        String name,
        String discountableType,
        Long typeId,
        Long scopeId,
        BigDecimal amount,
        LocalDateTime startDate,
        LocalDateTime expireDate,
        Integer maxUses,
        String description,
        List<Long> targets
) {
}

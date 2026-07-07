package com.sashia.ecommerce.domain.discount.common;

import com.sashia.ecommerce.domain.discount.Discount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountEditDTO(
        String name,
        String discountableType,
        Long typeId,
        Long scopeId,
        BigDecimal amount,
        Discount.SelectionType selectionType,
        LocalDateTime startDate,
        LocalDateTime expireDate,
        Integer maxUses,
        Boolean isActive,
        String description
) {
}

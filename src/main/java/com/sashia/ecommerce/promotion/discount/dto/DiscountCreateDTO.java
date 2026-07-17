package com.sashia.ecommerce.promotion.discount.dto;

import com.sashia.ecommerce.promotion.discount.Discount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DiscountCreateDTO(
        String name,
        String discountableType,
        Long typeId,
        Long scopeId,
        BigDecimal amount,
        Discount.SelectionType selectionType,
        LocalDateTime startDate,
        LocalDateTime expireDate,
        Integer maxUses,
        String description,
        List<Long> targets
) {
}

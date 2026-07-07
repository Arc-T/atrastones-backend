package com.sashia.ecommerce.domain.discount.common;

import com.sashia.ecommerce.domain.discount.Discount;

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

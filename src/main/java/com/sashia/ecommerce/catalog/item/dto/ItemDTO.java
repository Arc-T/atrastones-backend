package com.sashia.ecommerce.catalog.item.dto;

import com.sashia.ecommerce.promotion.engine.dto.AppliedPromotion;

import java.math.BigDecimal;
import java.util.List;

public record ItemDTO(
        Long id,
        ItemType type,
        String title,
        BigDecimal basePrice,
        Long categoryId,
        List<AppliedPromotion> promotions) {
}

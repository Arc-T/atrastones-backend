package com.sashia.ecommerce.promotion.engine.dto;

import com.sashia.ecommerce.promotion.type.TypeCode;

import java.math.BigDecimal;

/**
 * Represents the result of applying a single promotion to an item.
 *
 * <p>Each application stores the item's price before and after the promotion
 * was applied, making the pricing history fully traceable.
 */
public record AppliedPromotion(
        Long promotionId,
        String promotionName,
        TypeCode promotionType,
        BigDecimal discountAmount,
        BigDecimal priceBefore,
        BigDecimal priceAfter) {
}
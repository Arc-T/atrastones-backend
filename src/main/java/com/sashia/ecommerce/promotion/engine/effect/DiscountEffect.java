package com.sashia.ecommerce.promotion.engine.effect;

import java.util.List;

/**
 * Represents all discounts produced by a single promotion.
 *
 * <p>The map key is the affected item identifier and the value is the
 * discount amount that should be applied to that item.
 */
public record DiscountEffect(
        Long promotionId,
        List<DiscountedItem> discountedItems) implements PromotionEffect {
}
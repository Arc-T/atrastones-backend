package com.sashia.ecommerce.promotion.engine.effect;

import java.math.BigDecimal;

public record DiscountedItem(
        Long itemId,
        BigDecimal amount) {
}
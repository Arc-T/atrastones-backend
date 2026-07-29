package com.sashia.ecommerce.promotion.engine.dto;

import java.math.BigDecimal;

public record DiscountedItem(
        Long itemId,
        BigDecimal amount) {
}
package com.sashia.ecommerce.catalog.item.dto;

import java.math.BigDecimal;

public record ItemDTO(
        Long id,
        ItemType type,
        String title,
        BigDecimal basePrice,
        Long categoryId) {
}

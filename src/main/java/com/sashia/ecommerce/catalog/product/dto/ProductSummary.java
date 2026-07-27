package com.sashia.ecommerce.catalog.product.dto;

import java.time.LocalDateTime;

public record ProductSummary(
        Long id,
        String title,
        ProductPriceDTO discount,
        String coverImage,
        boolean isFeatured,
        LocalDateTime createdAt
) {

}
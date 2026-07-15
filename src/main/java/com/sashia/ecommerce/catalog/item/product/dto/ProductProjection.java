package com.sashia.ecommerce.catalog.item.product.dto;

import java.time.LocalDateTime;

public record ProductProjection(
        Long id,
        String name,
        Long categoryId,
        Long shopId,
        Integer quantity,
        Long serviceGroupId,
        String description,
        ProductStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
}
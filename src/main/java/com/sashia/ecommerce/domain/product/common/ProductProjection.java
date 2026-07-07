package com.sashia.ecommerce.domain.product.common;

import com.sashia.ecommerce.domain.product.ProductStatus;

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
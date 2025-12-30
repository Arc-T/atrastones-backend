package com.atrastones.shop.dto.projection;

import com.atrastones.shop.type.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductProjection(
        Long id,
        String name,
        Long categoryId,
        Long shopId,
        Integer quantity,
        BigDecimal price,
        Long serviceGroupId,
        String description,
        Long discountAmount,
        ProductStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {}

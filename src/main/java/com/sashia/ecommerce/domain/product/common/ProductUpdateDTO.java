package com.sashia.ecommerce.domain.product.common;

import com.sashia.ecommerce.domain.product.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        Long id,
        @NotNull String name,
        Long categoryId,
        Long shopId,
        @Min(0) Integer quantity,
        @NotNull @Min(0) BigDecimal price,
        Long serviceGroupId,
        ProductStatus status,
        @NotNull String description
) {
}

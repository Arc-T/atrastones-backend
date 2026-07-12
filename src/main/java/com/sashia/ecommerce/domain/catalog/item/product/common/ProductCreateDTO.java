package com.sashia.ecommerce.domain.catalog.item.product.common;

import java.math.BigDecimal;

public record ProductCreateDTO(String name,
                               BigDecimal price,
                               Long quantity,
                               Long categoryId,
                               String description,
                               Long serviceGroupId) {
}

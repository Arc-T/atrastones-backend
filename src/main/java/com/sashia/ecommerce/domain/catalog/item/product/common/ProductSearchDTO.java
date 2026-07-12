package com.sashia.ecommerce.domain.catalog.item.product.common;

import java.util.List;

public record ProductSearchDTO(
        List<Long> categoryIds,
        List<Long> attributeIds
) {
}
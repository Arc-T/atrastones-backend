package com.sashia.ecommerce.catalog.item.product.dto;

import java.util.List;

public record ProductSearchDTO(
        List<Long> categoryIds,
        List<Long> attributeIds
) {
}
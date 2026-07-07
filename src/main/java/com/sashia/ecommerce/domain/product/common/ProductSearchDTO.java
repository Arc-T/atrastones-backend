package com.sashia.ecommerce.domain.product.common;

import java.util.List;

public record ProductSearchDTO(
        List<Long> categoryIds,
        List<Long> attributeIds
) {
}
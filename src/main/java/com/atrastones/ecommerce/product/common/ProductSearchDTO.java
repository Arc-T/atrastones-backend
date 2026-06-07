package com.atrastones.ecommerce.product.common;

import java.util.List;

public record ProductSearchDTO(
        List<Long> categoryIds,
        List<Long> attributeIds
) {
}
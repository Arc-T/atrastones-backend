package com.sashia.ecommerce.domain.catalog.attribute.dto;

import org.jspecify.annotations.Nullable;

public record AttributeSearchRequest(
        @Nullable
        String name
) {
}
package com.sashia.ecommerce.domain.catalog.tag.dto;

import org.jspecify.annotations.Nullable;

public record TagSearchRequest(
        @Nullable String name
) {
}

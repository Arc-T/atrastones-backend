package com.sashia.ecommerce.domain.category.dto;

import org.jspecify.annotations.Nullable;

public record CategorySearchDTO(
        @Nullable String name,
        @Nullable Boolean onlyParents,
        @Nullable Boolean onlyChildren
) {
}
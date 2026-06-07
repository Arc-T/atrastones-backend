package com.atrastones.ecommerce.category.common;

import org.jspecify.annotations.Nullable;

public record CategorySearchDTO(
        @Nullable String name,
        @Nullable Boolean onlyParents,
        @Nullable Boolean onlyChildren
) {
}
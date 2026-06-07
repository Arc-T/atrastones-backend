package com.atrastones.ecommerce.attribute.common;

import org.jspecify.annotations.Nullable;

public record AttributeSearchDTO(
        @Nullable
        String name
) {
}
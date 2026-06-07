package com.atrastones.ecommerce.attribute.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AttributeUpdateDTO(
        @NotEmpty(message = "{attribute.name.required}")
        String name,
        @NotNull(message = "{category.id.required}")
        Long categoryId,
        @NotNull(message = "{attribute.type.required}")
        String type,
        @NotNull(message = "{attribute.isFilterable.required}")
        Boolean isFilterable
) {
}

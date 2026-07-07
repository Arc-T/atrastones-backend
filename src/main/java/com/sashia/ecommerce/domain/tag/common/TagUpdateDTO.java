package com.sashia.ecommerce.domain.tag.common;

import jakarta.validation.constraints.NotBlank;

public record TagUpdateDTO(
        @NotBlank(message = "{tag.name.required}")
        String name
) {
}

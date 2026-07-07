package com.sashia.ecommerce.domain.tag.common;

import jakarta.validation.constraints.NotBlank;

public record TagCreateDTO(
        @NotBlank(message = "{tag.name.required}")
        String name
) {
}

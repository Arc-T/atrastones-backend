package com.atrastones.ecommerce.tag.common;

import jakarta.validation.constraints.NotBlank;

public record TagCreateDTO(
        @NotBlank(message = "{tag.name.required}")
        String name
) {
}

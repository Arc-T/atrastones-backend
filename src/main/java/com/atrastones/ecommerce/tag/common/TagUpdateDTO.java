package com.atrastones.ecommerce.tag.common;

import jakarta.validation.constraints.NotBlank;

public record TagUpdateDTO(
        @NotBlank(message = "{tag.name.required}")
        String name
) {
}

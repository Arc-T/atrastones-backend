package com.atrastones.ecommerce.service.group.common;

import jakarta.validation.constraints.NotBlank;

public record ServiceGroupCreateDTO(
        @NotBlank(message = "{serviceGroup.name.required}")
        String name,
        String description
) {
}

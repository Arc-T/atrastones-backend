package com.sashia.ecommerce.domain.service.group.common;

import jakarta.validation.constraints.NotBlank;

public record ServiceGroupUpdateDTO(
        @NotBlank(message = "{serviceGroup.name.required}")
        String name,
        String description
) {
}

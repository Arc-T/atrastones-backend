package com.sashia.ecommerce.domain.catalog.item.serviceoffering.group.common;

import jakarta.validation.constraints.NotBlank;

public record ServiceGroupCreateDTO(
        @NotBlank(message = "{serviceGroup.name.required}")
        String name,
        String description
) {
}

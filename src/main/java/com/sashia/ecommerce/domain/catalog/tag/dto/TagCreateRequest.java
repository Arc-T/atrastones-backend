package com.sashia.ecommerce.domain.catalog.tag.dto;

import jakarta.validation.constraints.NotBlank;

public record TagCreateRequest(
        @NotBlank(message = "{tag.name.required}")
        String name
) {
}

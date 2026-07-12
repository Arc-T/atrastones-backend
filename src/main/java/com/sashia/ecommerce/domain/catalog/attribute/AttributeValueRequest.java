package com.sashia.ecommerce.domain.catalog.attribute;

import java.time.LocalDateTime;

public record AttributeValueRequest(
        Long id,
        String value,
        LocalDateTime createdAt
) {

    public AttributeValueRequest(String value) {
        this(null, value, null);
    }

}

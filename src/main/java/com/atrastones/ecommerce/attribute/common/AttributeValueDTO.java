package com.atrastones.ecommerce.attribute.common;

import com.atrastones.ecommerce.attribute.AttributeValue;

import java.time.LocalDateTime;

public record AttributeValueDTO(
        Long id,
        String value,
        LocalDateTime createdAt
) {
    // ********************** DTOs **********************
    public static AttributeValueDTO toDTO(AttributeValue attributeValue) {
        return new AttributeValueDTO(
                attributeValue.id(),
                attributeValue.value(),
                attributeValue.createdAt()
        );
    }

}

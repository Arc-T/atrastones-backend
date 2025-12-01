package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.AttributeValue;

import java.time.LocalDateTime;

public record AttributeValueDTO(
        Long id,
        String value,
        LocalDateTime createdAt
) {
    // ********************** DTOs **********************
    public static AttributeValueDTO toDTO(AttributeValue attributeValue) {
        return new AttributeValueDTO(
                attributeValue.getId(),
                attributeValue.getValue(),
                null
        );
    }

    public static AttributeValueDTO toFullDTO(AttributeValue attributeValue) {
        return new AttributeValueDTO(
                attributeValue.getId(),
                attributeValue.getValue(),
                attributeValue.getCreatedAt()
        );
    }

}

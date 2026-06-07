package com.atrastones.ecommerce.attribute.common;

import com.atrastones.ecommerce.attribute.Attribute;
import com.atrastones.ecommerce.category.common.CategoryDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record AttributeDTO(
        Long id,
        String name,
        Long categoryId,
        String type,
        Boolean isFilterable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        // ******************************* Relations *******************************
        CategoryDTO category,
        Set<AttributeValueDTO> attributeValues
) {
    // ********************************* DTOs ***********************************

    public static AttributeDTO toDTO(Attribute attribute) {
        return new AttributeDTO(
                attribute.id(),
                attribute.name(),
                attribute.categoryId(),
                attribute.type(),
                attribute.isFilterable(),
                attribute.createdAt(),
                attribute.updatedAt(),
                null,
                null
        );
    }

}
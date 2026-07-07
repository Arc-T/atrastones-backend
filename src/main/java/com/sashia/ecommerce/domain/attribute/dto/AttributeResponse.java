package com.sashia.ecommerce.domain.attribute.dto;

import com.sashia.ecommerce.domain.attribute.AttributeType;
import com.sashia.ecommerce.domain.attribute.value.AttributeValueRequest;
import com.sashia.ecommerce.domain.category.dto.CategoryDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record AttributeResponse(
        Long id,
        String name,
        Long categoryId,
        AttributeType type,
        Boolean isFilterable,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        // ******************************* Relations *******************************
        CategoryDTO category,
        Set<AttributeValueRequest> values
) {
}
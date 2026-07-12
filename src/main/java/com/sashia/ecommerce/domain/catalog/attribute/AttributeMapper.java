package com.sashia.ecommerce.domain.catalog.attribute;

import com.sashia.ecommerce.domain.catalog.attribute.dto.AttributeCreateRequest;
import com.sashia.ecommerce.domain.catalog.attribute.dto.AttributeResponse;
import com.sashia.ecommerce.domain.catalog.attribute.dto.AttributeUpdateRequest;
import com.sashia.ecommerce.domain.catalog.category.Category;
import com.sashia.ecommerce.domain.catalog.category.CategoryMapper;

import java.util.stream.Collectors;

public final class AttributeMapper {

    private AttributeMapper() {
    }

    public static AttributeResponse toDTO(Attribute attribute) {
        return new AttributeResponse(
                attribute.getId(),
                attribute.getName(),
                attribute.getCategoryId(),
                attribute.getType(),
                attribute.getFilterable(),
                attribute.getDescription(),
                attribute.getCreatedAt(),
                attribute.getUpdatedAt(),
                CategoryMapper.toDTO(attribute.getCategory()),
                attribute.getAttributeValues()
                        .stream().map(AttributeValueMapper::toDTO).collect(Collectors.toUnmodifiableSet())
        );
    }

    public static Attribute toEntity(AttributeCreateRequest dto, Category category) {
        Attribute attribute = new Attribute();
        attribute.setName(dto.name());
        attribute.setCategory(category);
        attribute.setFilterable(dto.isFilterable());
        attribute.setType(dto.type());
        attribute.setDescription(dto.description());
        return attribute;
    }

    public static void update(Attribute attribute, AttributeUpdateRequest request) {
        attribute.setName(request.name());
        attribute.setType(request.type());
        attribute.setDescription(request.description());
        attribute.setFilterable(request.isFilterable());
    }

}
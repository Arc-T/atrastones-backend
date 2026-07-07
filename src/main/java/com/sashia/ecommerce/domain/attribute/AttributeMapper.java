package com.sashia.ecommerce.domain.attribute;

import com.sashia.ecommerce.domain.attribute.dto.AttributeCreateRequest;
import com.sashia.ecommerce.domain.attribute.dto.AttributeResponse;
import com.sashia.ecommerce.domain.attribute.dto.AttributeUpdateRequest;
import com.sashia.ecommerce.domain.attribute.value.AttributeValueMapper;
import com.sashia.ecommerce.domain.category.Category;
import com.sashia.ecommerce.domain.category.CategoryMapper;

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

    public static void update(Attribute attribute, AttributeUpdateRequest dto, Category category) {
        attribute.setName(dto.name());
        attribute.setCategory(category);
        attribute.setFilterable(dto.isFilterable());
        attribute.setType(dto.type());
        attribute.setDescription(dto.description());
    }

}
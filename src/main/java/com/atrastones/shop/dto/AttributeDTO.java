package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Attribute;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record AttributeDTO(
        Long id,
        String name,
        Long categoryId,
        String type,
        Boolean isFilterable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) CategoryDTO category,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Set<AttributeValueDTO> attributeValues
) {
    // ********************** DTOs **********************
    public static AttributeDTO toDTO(Attribute attribute) {
        return new AttributeDTO(
                attribute.getId(),
                attribute.getName(),
                attribute.getCategory().getId(),
                "TEXT", //TODO: remove this hardcoded line
                attribute.isFilterable(),
                attribute.getCreatedAt(),
                attribute.getUpdatedAt(),
                null,
                null
        );
    }

    public static AttributeDTO toFullDTO(Attribute attribute) {

        Set<AttributeValueDTO> attributeValues = attribute.getAttributeValuesPivot().stream()
                .map(value -> AttributeValueDTO.toDTO(value.getAttributeValue()))
                .collect(Collectors.toSet());

        return new AttributeDTO(
                attribute.getId(),
                attribute.getName(),
                attribute.getCategory().getId(),
                "TEXT", //TODO: remove this hardcoded line
                attribute.isFilterable(),
                attribute.getCreatedAt(),
                attribute.getUpdatedAt(),
                CategoryDTO.toFullDTO(attribute.getCategory()),
                attributeValues
        );
    }

}

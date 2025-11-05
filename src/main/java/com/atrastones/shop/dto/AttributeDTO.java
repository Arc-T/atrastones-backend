package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Attribute;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@Jacksonized
public class AttributeDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    private String type;

    private Boolean isFilterable;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ********************** Relation **********************

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CategoryDTO category;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<AttributeValueDTO> attributeValues;

    // ********************** DTOs **********************

    public static AttributeDTO toDTO(Attribute attribute) {
        return AttributeDTO.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .isFilterable(attribute.isFilterable())
                .categoryId(attribute.getCategory().getId())
                .build();
    }

    public static AttributeDTO toFullDTO(Attribute attribute) {

        Set<AttributeValueDTO> attributeValues = attribute.getAttributeValuesPivot().stream()
                .map(value -> AttributeValueDTO.toDTO(value.getAttributeValue()))
                .collect(Collectors.toSet());

        return AttributeDTO.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .isFilterable(attribute.isFilterable())
                .createdAt(attribute.getCreatedAt())
                .updatedAt(attribute.getUpdatedAt())
                .category(CategoryDTO.toEntity(attribute.getCategory()))
                .attributeValues(attributeValues)
                .build();
    }

}

package com.atrastones.shop.dto;

import java.time.LocalDateTime;

import com.atrastones.shop.model.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryDTO(
        Long id,
        String name,
        String url,
        String icon,
        Long parentId,
        Integer displayOrder,
        String description,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) LocalDateTime createdAt,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) LocalDateTime updatedAt

) {
    // ********************** DTOs **********************
    public static CategoryDTO toEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getUrl(),
                category.getIcon(),
                category.getParentId(),
                category.getDisplayOrder(),
                category.getDescription(),
                null,
                null
                );
    }

    public static CategoryDTO toFullDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getUrl(),
                category.getIcon(),
                category.getParentId(),
                category.getDisplayOrder(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

}

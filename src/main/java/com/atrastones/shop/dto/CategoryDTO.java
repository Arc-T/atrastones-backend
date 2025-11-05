package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class CategoryDTO {

    private Long id;

    private String name;

    private String url;

    private String icon;

    @JsonProperty("parent_id")
    private Long parentId;

    private Integer displayOrder;

    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @Nullable
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;

    // ********************** DTOs **********************

    public static CategoryDTO toEntity(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .url(category.getUrl())
                .icon(category.getIcon())
                .parentId(category.getParentId())
                .displayOrder(category.getDisplayOrder())
                .description(category.getDescription())
                .build();
    }

    public static CategoryDTO toFullDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .url(category.getUrl())
                .icon(category.getIcon())
                .parentId(category.getParentId())
                .displayOrder(category.getDisplayOrder())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

}

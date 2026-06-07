package com.atrastones.ecommerce.category.common;

import com.atrastones.ecommerce.category.Category;

import java.time.LocalDateTime;

public record CategoryDTO(
        Long id,
        String name,
        String url,
        String icon,
        Long parentId,
        Integer displayOrder,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    // ********************** DTOs **********************
    public static CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.id(),
                category.name(),
                category.url(),
                category.icon(),
                category.parentId(),
                category.displayOrder(),
                category.description(),
                category.createdAt(),
                category.updatedAt()
        );
    }

}

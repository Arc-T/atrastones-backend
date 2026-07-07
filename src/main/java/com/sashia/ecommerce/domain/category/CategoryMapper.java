package com.sashia.ecommerce.domain.category;

import com.sashia.ecommerce.domain.category.dto.CategoryDTO;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDisplayOrder(dto.displayOrder());
        category.setIcon(dto.icon());
        category.setDescription(dto.description());
        return category;
    }

    public static CategoryDTO toDTO(Category category) {
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

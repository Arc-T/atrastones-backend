package com.atrastones.shop.mapper.implement;

import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.mapper.contract.CategoryMapper;
import com.atrastones.shop.model.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImp implements CategoryMapper {

    @Override
    public CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .url(category.getUrl())
                .icon(category.getIcon())
                .displayOrder(category.getDisplayOrder())
                .description(category.getDescription())
                .build();
    }

    @Override
    public CategoryDTO toFullDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .url(category.getUrl())
                .icon(category.getIcon())
                .displayOrder(category.getDisplayOrder())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        return null;
    }

}

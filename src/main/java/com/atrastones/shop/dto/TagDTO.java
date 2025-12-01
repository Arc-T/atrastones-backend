package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Tag;

public record TagDTO(
        Long id,
        String name
) {
    // *********************** DTO ***********************
    public static TagDTO toDTO(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getName()
        );
    }

}

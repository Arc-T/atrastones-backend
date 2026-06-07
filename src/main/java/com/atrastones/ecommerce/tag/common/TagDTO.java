package com.atrastones.ecommerce.tag.common;

import com.atrastones.ecommerce.tag.Tag;

public record TagDTO(
        Long id,
        String name
) {
    // *********************** DTO ***********************
    public static TagDTO toDTO(Tag tag) {
        return new TagDTO(
                tag.id(),
                tag.name()
        );
    }

}

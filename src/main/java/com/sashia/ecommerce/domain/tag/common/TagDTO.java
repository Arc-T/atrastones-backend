package com.sashia.ecommerce.domain.tag.common;

import com.sashia.ecommerce.domain.tag.Tag;

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

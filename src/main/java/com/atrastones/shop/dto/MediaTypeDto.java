package com.atrastones.shop.dto;


import com.atrastones.shop.model.entity.MediaType;

public record MediaTypeDto(
        Long id,
        String name,
        String description
) {
    // ********************** DTOs **********************
    public static MediaTypeDto toDTO(MediaType mediaType) {
        return new MediaTypeDto(
                mediaType.getId(),
                mediaType.getName(),
                mediaType.getDescription()
        );
    }

}

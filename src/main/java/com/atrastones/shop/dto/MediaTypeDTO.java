package com.atrastones.shop.dto;


import com.atrastones.shop.model.entity.MediaType;

public record MediaTypeDTO(
        Long id,
        String name,
        String description
) {
    // ********************** DTOs **********************
    public static MediaTypeDTO toDTO(MediaType mediaType) {
        return new MediaTypeDTO(
                mediaType.getId(),
                mediaType.getName(),
                mediaType.getDescription()
        );
    }

}

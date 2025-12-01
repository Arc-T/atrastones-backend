package com.atrastones.shop.dto;


import com.atrastones.shop.model.entity.MediaType;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

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

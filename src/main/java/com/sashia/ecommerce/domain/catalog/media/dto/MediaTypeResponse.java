package com.sashia.ecommerce.domain.catalog.media.dto;

import com.sashia.ecommerce.domain.catalog.media.MediaType;

public record MediaTypeResponse(
        Long id,
        String name,
        String description
) {
    // ********************** DTOs **********************
    public static MediaTypeResponse toDTO(MediaType mediaType) {
        return new MediaTypeResponse(
                mediaType.id(),
                mediaType.name(),
                mediaType.description()
        );
    }

}

package com.atrastones.ecommerce.product.media;


public record MediaTypeDTO(
        Long id,
        String name,
        String description
) {
    // ********************** DTOs **********************
    public static MediaTypeDTO toDTO(MediaType mediaType) {
        return new MediaTypeDTO(
                mediaType.id(),
                mediaType.name(),
                mediaType.description()
        );
    }

}

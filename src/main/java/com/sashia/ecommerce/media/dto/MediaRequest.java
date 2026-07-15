package com.sashia.ecommerce.media.dto;

import com.sashia.ecommerce.media.Media;

import java.time.LocalDateTime;

public record MediaRequest(
        Long id,
        Long productId,
        String url,
        String type,
        Integer displayOrder,
        String extension,
        LocalDateTime createdAt
) {

    public MediaRequest(Long productId,
                        String url,
                        String type,
                        Integer displayOrder,
                        String extension,
                        LocalDateTime createdAt) {
        this(null, productId, url, type, displayOrder, extension, createdAt);
    }

    public static MediaRequest toDTO(Media media) {
        return new MediaRequest(
                media.id(),
                media.product().getId(),
                media.url(),
                media.mediaType().name(),
                media.displayOrder(),
                media.extension(),
                media.createdAt()
        );
    }

}

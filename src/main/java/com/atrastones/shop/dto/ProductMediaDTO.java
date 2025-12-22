package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.ProductMedia;

import java.time.LocalDateTime;

public record ProductMediaDTO(
        Long id,
        Long productId,
        String url,
        String type,
        Integer displayOrder,
        String extension,
        LocalDateTime createdAt
) {

    public ProductMediaDTO(Long productId,
                           String url,
                           String type,
                           Integer displayOrder,
                           String extension,
                           LocalDateTime createdAt) {
        this(null, productId, url, type, displayOrder, extension, createdAt);
    }

    // ******************************** DTO ********************************
    public static ProductMediaDTO toDTO(ProductMedia productMedia) {
        return new ProductMediaDTO(
                productMedia.getId(),
                productMedia.getProduct().getId(),
                productMedia.getUrl(),
                productMedia.getMediaType().getName(),
                productMedia.getDisplayOrder(),
                productMedia.getExtension(),
                productMedia.getCreatedAt()
        );
    }

}

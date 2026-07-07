package com.sashia.ecommerce.domain.product;

import com.sashia.ecommerce.domain.product.media.ProductMedia;

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

    public static ProductMediaDTO toDTO(ProductMedia productMedia) {
        return new ProductMediaDTO(
                productMedia.id(),
                productMedia.product().id(),
                productMedia.url(),
                productMedia.mediaType().name(),
                productMedia.displayOrder(),
                productMedia.extension(),
                productMedia.createdAt()
        );
    }

}

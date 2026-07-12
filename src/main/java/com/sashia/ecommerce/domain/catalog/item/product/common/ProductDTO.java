package com.sashia.ecommerce.domain.catalog.item.product.common;

import com.sashia.ecommerce.domain.catalog.item.product.ProductPriceDTO;
import com.sashia.ecommerce.domain.catalog.item.product.ProductStatus;
import com.sashia.ecommerce.domain.catalog.media.dto.MediaRequest;

import java.time.LocalDateTime;
import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        Long shopId,
        Integer quantity,
        ProductStatus status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        // ===================================== RELATIONS =====================================
//        CategoryDTO category,
        List<MediaRequest> media,
//        ServiceGroupDTO serviceGroup,
        ProductPriceDTO price
) {

}
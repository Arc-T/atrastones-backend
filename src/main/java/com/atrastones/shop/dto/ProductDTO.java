package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Product;
import com.atrastones.shop.type.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        Long shopId,
        Integer quantity,
        BigDecimal price,
        ProductStatus status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,

        // ===================================== RELATIONS =====================================
        CategoryDTO category,
        List<ProductMediaDTO> media,
        ServiceGroupDTO serviceGroup
) {
    // ===================================== DTOs =====================================
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getShop().getId(),
                product.getQuantity(),
                product.getPrice(),
                product.getStatus(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                CategoryDTO.toDTO(product.getCategory()),
                product.getMedia().stream().map(ProductMediaDTO::toDTO).toList(),
                ServiceGroupDTO.toDTO(product.getServiceGroup())
        );

    }

}

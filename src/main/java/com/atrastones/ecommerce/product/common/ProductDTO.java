package com.atrastones.ecommerce.product.common;

import com.atrastones.ecommerce.product.Product;
import com.atrastones.ecommerce.product.ProductMediaDTO;
import com.atrastones.ecommerce.product.ProductPriceDTO;
import com.atrastones.ecommerce.product.ProductStatus;

import java.math.BigDecimal;
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
        List<ProductMediaDTO> media,
//        ServiceGroupDTO serviceGroup,
        ProductPriceDTO price
) {
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.id(),
                product.name(),
                product.shopId(),
                product.quantity(),
                product.status(),
                product.description(),
                product.createdAt(),
                product.updatedAt(),
//                CategoryDTO.toDTO(product.category()),
                product.media().stream().map(ProductMediaDTO::toDTO).toList(),
//                ServiceGroupDTO.toDTO(product.serviceGroup()),
                ProductPriceDTO.toDTO(product.prices().stream().findFirst().get().basePrice(), BigDecimal.ZERO)
        );
    }

    public static ProductDTO toDTO(Product product, ProductPriceDTO price) {
        return new ProductDTO(
                product.id(),
                product.name(),
                product.shopId(),
                product.quantity(),
                product.status(),
                product.description(),
                product.createdAt(),
                product.updatedAt(),
//                CategoryDTO.toDTO(product.category()),
                product.media().stream().map(ProductMediaDTO::toDTO).toList(),
//                ServiceGroupDTO.toDTO(product.serviceGroup()),
                price
        );
    }

}
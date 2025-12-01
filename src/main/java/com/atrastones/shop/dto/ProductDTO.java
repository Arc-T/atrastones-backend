package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Product;
import com.atrastones.shop.type.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ProductDTO(
        Long id,
        @NotNull String name,
        Long shopId,
        @Min(0) Integer quantity,
        @NotNull @Min(0) Integer price,
        Long serviceGroupId,
//        Long discountId,
//        Integer discountAmount,
        ProductStatus status,
        @NotNull String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        // ********************** RELATIONS **********************
//        OfferDTO offerGroup,
        CategoryDTO category,
        List<ProductMediaDTO> media
) {
    // ********************** DTOs **********************
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getShop().getId(),
                product.getQuantity(),
                product.getPrice(),
                product.getOfferingGroup().getId(),
//                .discountId(product.getDiscount().getId())
//                .discountAmount(product.getDiscount().getAmount())
                product.getStatus(),
                product.getDescription(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
//                OfferDTO.toDTO(product.getOfferingGroup()),
                CategoryDTO.toEntity(product.getCategory()),
                product.getMedia().stream().map(ProductMediaDTO::toDTO).toList()
                );

    }

}

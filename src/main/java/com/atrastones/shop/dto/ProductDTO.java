package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Product;
import com.atrastones.shop.type.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class ProductDTO {

    private Long id;

    @NotNull
    private String name;

    private Long categoryId;

    private Long shopId;

    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private Integer price;

    private Long serviceGroupId;

    private Long discountId;

    private Integer discountAmount;

    private ProductStatus status;

    @NotNull
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ********************** DTOs **********************

    public static ProductDTO toDTO(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategory().getId())
                .shopId(product.getShop().getId())
                .quantity(product.getQuantity())
                .price(product.getPrice())
//                .discountId(product.getDiscount().getId())
//                .discountAmount(product.getDiscount().getAmount())
                .status(product.getStatus())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

    }

}

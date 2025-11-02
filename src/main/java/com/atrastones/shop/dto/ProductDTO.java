package com.atrastones.shop.dto;

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

}

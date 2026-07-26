package com.sashia.ecommerce.promotion.dto;

import java.time.LocalDateTime;

public record DiscountTypeDTO(
        Long id,
        DiscountType.DiscountTypeType code,
        String name,
        String description,
        LocalDateTime createdAt
) {

    public static DiscountTypeDTO toDTO(DiscountType discountType) {
        return new DiscountTypeDTO(
                discountType.id(),
                discountType.code(),
                discountType.name(),
                discountType.description(),
                discountType.createdAt()
        );
    }

}

package com.sashia.ecommerce.promotion.discount.dto;

import com.sashia.ecommerce.promotion.discount.internal.DiscountTarget;

import java.time.LocalDateTime;

public record DiscountTargetDTO(
        long id,
        DiscountTarget.DiscountTargetType targetType,
        long targetId,
        LocalDateTime createdAt
) {

    public static DiscountTargetDTO toDTO(DiscountTarget discountTarget) {
        return new DiscountTargetDTO(
                discountTarget.id(),
                discountTarget.targetType(),
                discountTarget.targetId(),
                discountTarget.createdAt()
        );
    }

}

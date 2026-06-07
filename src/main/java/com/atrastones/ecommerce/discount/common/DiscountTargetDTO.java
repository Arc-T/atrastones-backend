package com.atrastones.ecommerce.discount.common;

import com.atrastones.ecommerce.discount.DiscountTarget;

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

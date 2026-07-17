package com.sashia.ecommerce.promotion.discount.dto;

import com.sashia.ecommerce.promotion.discount.Discount;
import com.sashia.ecommerce.promotion.discount.Discount.SelectionType;
import com.sashia.ecommerce.promotion.discount.internal.DiscountScope;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DiscountDTO(
        Long id,
        String name,
        String discountableType,
        BigDecimal amount,
        SelectionType selectionType,
        LocalDateTime startDate,
        LocalDateTime expireDate,
        Integer maxUses,
        Integer usedCount,
        Boolean isActive,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt,
        // ********************** RELATIONS **********************
        List<DiscountTargetDTO> targets,
        DiscountScope.DiscountScopeType discountScope,
        DiscountTypeDTO type
) {

    public static DiscountDTO toDTO(Discount discount) {
        return new DiscountDTO(
                discount.id(),
                discount.name(),
                discount.discountableType(),
                discount.amount(),
                discount.selectionType(),
                discount.startDate(),
                discount.expireDate(),
                discount.maxUses(),
                discount.usedCount(),
                discount.isActive(),
                discount.description(),
                discount.createdAt(),
                discount.updatedAt(),
                discount.deletedAt(),
                discount.discountTargets().stream().map(DiscountTargetDTO::toDTO).toList(),
                discount.discountScope().code(),
                DiscountTypeDTO.toDTO(discount.discountType())
        );
    }

}
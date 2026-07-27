package com.sashia.ecommerce.promotion.dto;

import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.scope.ScopeCode;
import com.sashia.ecommerce.promotion.target.dto.TargetDTO;
import com.sashia.ecommerce.promotion.target.type.TargetTypeCode;
import com.sashia.ecommerce.promotion.type.TypeCode;
import com.sashia.ecommerce.promotion.discount.dto.DiscountDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PromotionDTO(
        Long id,
        String name,
        String discountableType,
        BigDecimal amount,
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
        TargetTypeCode targetType,
        DiscountDTO discount,
        ScopeCode scope,
        TypeCode type,
        List<TargetDTO> targets,
        List<ConditionDTO> conditions) {
}
package com.sashia.ecommerce.promotion.dto;

import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.discount.dto.DiscountDTO;
import com.sashia.ecommerce.promotion.scope.ScopeCode;
import com.sashia.ecommerce.promotion.target.dto.TargetDTO;
import com.sashia.ecommerce.promotion.target.type.TargetTypeCode;
import com.sashia.ecommerce.promotion.type.TypeCode;

import java.time.LocalDateTime;
import java.util.List;

public record PromotionDTO(
        Long id,
        String name,
        LocalDateTime validFrom,
        LocalDateTime validUntil,
        Boolean isActive,
        String description,
        // ********************** RELATIONS **********************
        TargetTypeCode targetType,
        DiscountDTO discount,
        ScopeCode scope,
        TypeCode type,
        List<TargetDTO> targets,
        List<ConditionDTO> conditions) {
}
package com.sashia.ecommerce.promotion.dto;

import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.dto.dto.BuyXGetYDTO;
import com.sashia.ecommerce.promotion.scope.ScopeCode;
import com.sashia.ecommerce.promotion.target.dto.TargetDTO;
import com.sashia.ecommerce.promotion.type.TypeCode;
import com.sashia.ecommerce.promotion.type.coupon.dto.CouponDTO;
import com.sashia.ecommerce.promotion.type.discount.dto.DiscountDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PromotionEvaluationDTO(
        Long id,
        String name,
        TypeCode type,
        ScopeCode scope,
        Integer priority,
        boolean stackable,
        boolean active,
        LocalDateTime validFrom,
        LocalDateTime validUntil,
        // **************************** RELATIONS ****************************
        List<ConditionDTO> conditions,
        List<TargetDTO> targets,
        List<CouponDTO> coupons,
        DiscountDTO discount,
        BuyXGetYDTO buyXGetY
) {
}
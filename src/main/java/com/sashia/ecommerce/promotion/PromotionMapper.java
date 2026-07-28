package com.sashia.ecommerce.promotion;

import com.sashia.ecommerce.promotion.condition.ConditionMapper;
import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.discount.Discount;
import com.sashia.ecommerce.promotion.discount.DiscountMapper;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.target.TargetMapper;
import com.sashia.ecommerce.promotion.target.dto.TargetDTO;

import java.util.List;

public class PromotionMapper {

    public static PromotionDTO toDTO(Promotion promotion, Discount discount) {

        List<TargetDTO> targets = promotion.getTargets().isEmpty() ? List.of() :
                promotion.getTargets().stream().map(TargetMapper::toDTO).toList();

        List<ConditionDTO> conditions = promotion.getConditions().isEmpty() ? List.of() :
                promotion.getConditions().stream().map(ConditionMapper::toDTO).toList();

        return new PromotionDTO(
                promotion.getId(),
                promotion.getName(),
                promotion.getValidFrom(),
                promotion.getValidUntil(),
                promotion.isActive(),
                promotion.getDescription(),
                promotion.getTargetType().getCode(),
                DiscountMapper.toDTO(discount),
                promotion.getScope().getCode(),
                promotion.getType().getCode(),
                targets,
                conditions
        );
    }

}
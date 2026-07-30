package com.sashia.ecommerce.promotion.engine.condition;

import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.condition.type.ConditionTypeCode;
import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;

import java.util.List;

public interface ConditionEvaluator {

    ConditionTypeCode type();

    void evaluate(List<ConditionDTO> conditions, PromotionContext context);

}

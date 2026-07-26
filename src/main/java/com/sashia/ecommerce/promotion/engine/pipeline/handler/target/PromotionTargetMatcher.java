package com.sashia.ecommerce.promotion.engine.pipeline.handler.target;

import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.target.dto.TargetDTO;
import com.sashia.ecommerce.promotion.target.type.TargetTypeCode;

import java.util.List;

public interface PromotionTargetMatcher {

    TargetTypeCode type();

    TargetMatchResult matches(List<TargetDTO> targets, PromotionContext context);

}
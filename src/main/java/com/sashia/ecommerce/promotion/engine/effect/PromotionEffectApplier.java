package com.sashia.ecommerce.promotion.engine.effect;

import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;
import com.sashia.ecommerce.promotion.engine.dto.PromotionResult;

public interface PromotionEffectApplier {

    void apply(PromotionContext context, PromotionResult result);

}
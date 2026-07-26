package com.sashia.ecommerce.promotion.engine;

import com.sashia.ecommerce.promotion.engine.context.PromotionContext;

public interface PromotionEffectApplier {

    void apply(PromotionContext context, PromotionResult result);

}
package com.sashia.ecommerce.promotion.engine.strategy;

import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;
import com.sashia.ecommerce.promotion.type.TypeCode;

public interface PromotionStrategy {

    TypeCode type();

    void execute(PromotionContext context);

}

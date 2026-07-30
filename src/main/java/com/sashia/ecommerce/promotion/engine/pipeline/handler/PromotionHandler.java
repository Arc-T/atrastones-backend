package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;

public interface PromotionHandler {

    PromotionHandlerResult handle(PromotionContext context);

}

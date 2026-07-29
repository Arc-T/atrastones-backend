package com.sashia.ecommerce.promotion.engine;

import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import com.sashia.ecommerce.promotion.engine.dto.PromotionResult;

/**
 * Entry point of the promotion engine.
 *
 * <p>Evaluates all active promotions against the supplied request and returns
 * the resulting promotion effects.
 *
 * <p>The engine is read-only. It never persists data or mutates domain objects.
 * Its sole responsibility is to determine which promotions apply and what
 * effects they produce.
 */
public interface PromotionEngine {

    PromotionResult apply(PromotionRequest request);

}

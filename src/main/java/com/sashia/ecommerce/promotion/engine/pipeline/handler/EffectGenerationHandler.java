package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.strategy.PromotionStrategy;
import com.sashia.ecommerce.promotion.engine.strategy.PromotionStrategyFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Executes the promotion by delegating to the appropriate
 * {@link PromotionStrategy}.
 *
 * <p>At this stage the promotion has already passed activation,
 * scope, target and condition evaluation. The selected strategy
 * is responsible for producing one or more promotion effects and
 * storing them in the {@link PromotionContext}.
 */
@Component
@Order(value = 5)
public class EffectGenerationHandler implements PromotionHandler {

    private final PromotionStrategyFactory strategyFactory;

    public EffectGenerationHandler(PromotionStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public PromotionHandlerResult handle(PromotionContext context) {

        PromotionStrategy strategy =
                strategyFactory.resolve(context.getPromotion().type());

        strategy.execute(context);

        return PromotionHandlerResult.success();
    }

}
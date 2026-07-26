package com.sashia.ecommerce.promotion.engine;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.pipeline.PromotionPipeline;
import com.sashia.ecommerce.promotion.engine.price.PricedItem;
import com.sashia.ecommerce.promotion.engine.resolver.PromotionResolver;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Default implementation of the promotion engine.
 *
 * <p>The engine resolves all applicable promotions for the incoming request,
 * evaluates each promotion independently through the promotion pipeline and
 * aggregates the produced effects into a single {@link PromotionResult}.
 */
@Component
public class PromotionEngineImpl implements PromotionEngine {

    private final PromotionResolver promotionResolver;
    private final PromotionPipeline promotionPipeline;
    private final PromotionEffectApplier promotionEffectApplier;

    public PromotionEngineImpl(PromotionResolver promotionResolver, PromotionPipeline promotionPipeline, PromotionEffectApplier promotionEffectApplier) {
        this.promotionResolver = promotionResolver;
        this.promotionPipeline = promotionPipeline;
        this.promotionEffectApplier = promotionEffectApplier;
    }

    @Override
    public PromotionResult evaluate(PromotionRequest request) {

        PromotionResult result = new PromotionResult();

        initializePricedItems(request, result);

        List<PromotionDTO> promotions = promotionResolver.resolve(request);

        if (!promotions.isEmpty()) {

            for (PromotionDTO promotion : promotions) {

                PromotionContext context = new PromotionContext(promotion, request);

                promotionPipeline.execute(context);

                promotionEffectApplier.apply(context, result);
            }
        }

        return result;
    }

    private void initializePricedItems(PromotionRequest request, PromotionResult result) {

        request.items()
                .forEach(item -> result.addPricedItem(
                        new PricedItem(item))
                );
    }

}
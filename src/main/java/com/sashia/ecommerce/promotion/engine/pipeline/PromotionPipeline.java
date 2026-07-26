package com.sashia.ecommerce.promotion.engine.pipeline;

import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.pipeline.handler.PromotionHandler;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Executes the ordered promotion handlers for a single promotion.
 *
 * <p>Each handler may stop further processing by returning a failed
 * {@link com.sashia.ecommerce.promotion.engine.pipeline.handler.PromotionHandlerResult}.
 */
@Component
public class PromotionPipeline {

    private final List<PromotionHandler> handlers;

    public PromotionPipeline(List<PromotionHandler> handlers) {
        this.handlers = handlers;
    }

    public void execute(PromotionContext context) {

        for (var handler : handlers) {

            if (!handler.handle(context).proceed())
                break;

        }
    }

}
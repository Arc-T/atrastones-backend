package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Verifies that the current promotion can be evaluated against the
 * supplied request.
 *
 * <p>Each promotion is associated with a single {@code PromotionScope}
 * (for example: ITEM, USER or ORDER). This handler ensures that the
 * request contains the data required to evaluate that scope before
 * proceeding to target and condition evaluation.
 *
 * <p>If the required scope data is unavailable, the pipeline is
 * terminated immediately.
 */
@Component
@Order(value = 2)
public class ScopeHandler implements PromotionHandler {

    @Override
    public PromotionHandlerResult handle(PromotionContext context) {

        PromotionRequest request = context.getRequest();

        return switch (context.getPromotion().scope()) {
            case PRODUCT, SERVICE_OFFERING -> CollectionUtils.isEmpty(request.items()) ?
                    PromotionHandlerResult.failure("empty items") :
                    PromotionHandlerResult.success();
            case USER -> request.userId() == null ?
                    PromotionHandlerResult.failure("empty user") :
                    PromotionHandlerResult.success();
            case ORDER -> request.order() == null ?
                    PromotionHandlerResult.failure("empty order") :
                    PromotionHandlerResult.success();
        };
    }

}

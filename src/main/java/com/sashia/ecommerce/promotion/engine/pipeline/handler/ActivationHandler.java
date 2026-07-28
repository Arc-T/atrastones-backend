package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * First stage of the promotion pipeline.
 *
 * <p>Verifies that the promotion is currently eligible for evaluation.
 *
 * <p>A promotion is considered active when:
 * <ul>
 *     <li>It is enabled.</li>
 *     <li>It has not expired.</li>
 * </ul>
 *
 * <p>If the promotion is inactive or expired, the pipeline is terminated
 * immediately and no further handlers are executed.
 */
@Component
@Order(value = 1)
public class ActivationHandler implements PromotionHandler {

    @Override
    public PromotionHandlerResult handle(PromotionContext context) {
        LocalDateTime now = LocalDateTime.now();
        PromotionDTO promotion = context.getPromotion();

        boolean active = promotion.isActive()
                && !promotion.validFrom().isAfter(now)
                && !promotion.validUntil().isBefore(now);

        return active
                ? PromotionHandlerResult.success()
                : PromotionHandlerResult.failure("Promotion is not active");
    }

}


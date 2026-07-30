package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;
import com.sashia.ecommerce.promotion.engine.pipeline.handler.target.PromotionTargetMatcher;
import com.sashia.ecommerce.promotion.engine.pipeline.handler.target.PromotionTargetMatcherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Resolves and evaluates the promotion targets against the current request.
 *
 * <p>Each promotion defines a single target type (for example ITEM, CATEGORY,
 * BRAND or USER_SEGMENT). The corresponding {@link PromotionTargetMatcher}
 * determines whether the promotion applies and resolves the affected items.
 *
 * <p>Multiple target values are evaluated using OR semantics inside the matcher.
 *
 * <p>If no target matches, the promotion pipeline is terminated and the
 * promotion is not evaluated further.
 */
@Component
@Order(value = 3)
public class TargetHandler implements PromotionHandler {

    private static final Logger log = LoggerFactory.getLogger(TargetHandler.class);
    private final PromotionTargetMatcherFactory matcherFactory;

    public TargetHandler(PromotionTargetMatcherFactory matcherFactory) {
        this.matcherFactory = matcherFactory;
    }

    @Override
    public PromotionHandlerResult handle(PromotionContext context) {

        PromotionDTO promotion = context.getPromotion();

        if (!promotion.targets().isEmpty()) {

            PromotionTargetMatcher matcher = matcherFactory.get(promotion.targetType());

            log.debug("Promotion target matcher for {} is {}", promotion.targetType(), matcher);

            matcher.match(promotion.targets(), context);

            if (context.getCandidateItems().isEmpty())
                return PromotionHandlerResult.failure("Target not applicable");

        }

        return PromotionHandlerResult.success();
    }

}
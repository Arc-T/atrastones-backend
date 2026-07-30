package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.condition.type.ConditionTypeCode;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.condition.ConditionEvaluator;
import com.sashia.ecommerce.promotion.engine.condition.ConditionEvaluatorFactory;
import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Evaluates all promotion conditions.
 *
 * <p>Conditions are grouped by their type and delegated to the corresponding
 * {@link ConditionEvaluator}. Each evaluator determines whether its
 * group of conditions is satisfied.
 *
 * <p>Different condition types are evaluated using AND semantics. If any
 * evaluator fails, the promotion pipeline is terminated.
 */
@Component
@Order(value = 4)
public class ConditionHandler implements PromotionHandler {

    private static final Logger log = LoggerFactory.getLogger(ConditionHandler.class);
    private final ConditionEvaluatorFactory evaluatorFactory;

    public ConditionHandler(ConditionEvaluatorFactory evaluatorFactory) {
        this.evaluatorFactory = evaluatorFactory;
    }

    @Override
    public PromotionHandlerResult handle(PromotionContext context) {

        PromotionDTO promotion = context.getPromotion();

        if (!promotion.conditions().isEmpty()) {

            Map<ConditionTypeCode, List<ConditionDTO>> groupedConditions =
                    groupConditionsByType(promotion);

            applyConditions(groupedConditions, context);

            if (context.getCandidateItems().isEmpty())
                return PromotionHandlerResult.failure("Condition not satisfied");
        }

        log.debug("Promotion conditions satisfied by {} conditions", promotion.conditions().size());

        return PromotionHandlerResult.success();

    }

    private Map<ConditionTypeCode, List<ConditionDTO>> groupConditionsByType(PromotionDTO promotion) {
        return promotion.conditions()
                .stream()
                .collect(Collectors.groupingBy(
                        ConditionDTO::type,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    private void applyConditions(Map<ConditionTypeCode, List<ConditionDTO>> groupedConditions,
                                 PromotionContext context) {

        for (Map.Entry<ConditionTypeCode, List<ConditionDTO>> entry : groupedConditions.entrySet()) {

            ConditionEvaluator evaluator = evaluatorFactory.resolve(entry.getKey());

            log.debug("Promotion condition evaluator: {}", evaluator);

            evaluator.evaluate(entry.getValue(), context);
        }
    }

}
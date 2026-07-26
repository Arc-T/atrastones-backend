package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.condition.type.ConditionTypeCode;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.condition.PromotionConditionEvaluator;
import com.sashia.ecommerce.promotion.engine.condition.PromotionConditionEvaluatorFactory;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
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
 * {@link PromotionConditionEvaluator}. Each evaluator determines whether its
 * group of conditions is satisfied.
 *
 * <p>Different condition types are evaluated using AND semantics. If any
 * evaluator fails, the promotion pipeline is terminated.
 */
@Component
@Order(value = 4)
public class ConditionHandler implements PromotionHandler {

    private final PromotionConditionEvaluatorFactory evaluatorFactory;

    public ConditionHandler(PromotionConditionEvaluatorFactory evaluatorFactory) {
        this.evaluatorFactory = evaluatorFactory;
    }

    @Override
    public PromotionHandlerResult handle(PromotionContext context) {

        PromotionDTO promotion = context.getPromotion();

        Map<ConditionTypeCode, List<ConditionDTO>> groupedConditions =
                groupConditionsByType(promotion);

        if (!conditionsSatisfied(groupedConditions, context)) {
            return PromotionHandlerResult.failure("Condition not satisfied");
        }

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

    private boolean conditionsSatisfied(Map<ConditionTypeCode, List<ConditionDTO>> groupedConditions,
                                        PromotionContext context) {

        for (Map.Entry<ConditionTypeCode, List<ConditionDTO>> entry
                : groupedConditions.entrySet()) {

            PromotionConditionEvaluator evaluator =
                    evaluatorFactory.get(entry.getKey());

            if (!evaluator.evaluate(entry.getValue(), context)) {
                return false;
            }
        }

        return true;
    }

}
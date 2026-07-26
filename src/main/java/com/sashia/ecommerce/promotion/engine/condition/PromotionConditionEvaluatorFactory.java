package com.sashia.ecommerce.promotion.engine.condition;

import com.sashia.ecommerce.promotion.condition.type.ConditionTypeCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PromotionConditionEvaluatorFactory {

    private final Map<ConditionTypeCode, PromotionConditionEvaluator> evaluators;

    public PromotionConditionEvaluatorFactory(List<PromotionConditionEvaluator> evaluators) {
        this.evaluators = evaluators.stream()
                .collect(Collectors.toMap(
                        PromotionConditionEvaluator::supports,
                        Function.identity()
                ));
    }

    public PromotionConditionEvaluator get(ConditionTypeCode type) {

        PromotionConditionEvaluator evaluator = evaluators.get(type);

        if (evaluator == null) {
            throw new IllegalStateException(
                    "No PromotionConditionEvaluator found for type: " + type
            );
        }

        return evaluator;
    }

}
package com.sashia.ecommerce.promotion.engine.strategy;

import com.sashia.ecommerce.promotion.type.TypeCode;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class PromotionStrategyFactory {

    private final Map<TypeCode, PromotionStrategy> promotionStrategies;

    public PromotionStrategyFactory(List<PromotionStrategy> promotionStrategies) {
        this.promotionStrategies = new LinkedHashMap<>(promotionStrategies.size());

        for (var strategy : promotionStrategies) {
            this.promotionStrategies.put(strategy.type(), strategy);
        }
    }

    public PromotionStrategy resolve(TypeCode type) {
        PromotionStrategy strategy = promotionStrategies.get(type);
        if (strategy == null) {
            throw new IllegalStateException(
                    "No PromotionStrategy found for type: " + type
            );
        }
        return strategy;
    }

}

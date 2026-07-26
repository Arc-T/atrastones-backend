package com.sashia.ecommerce.promotion.engine.pipeline.handler.target;

import com.sashia.ecommerce.promotion.target.type.TargetTypeCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PromotionTargetMatcherFactory {

    private final Map<TargetTypeCode, PromotionTargetMatcher> matchers;

    public PromotionTargetMatcherFactory(List<PromotionTargetMatcher> matchers) {

        this.matchers = matchers.stream()
                .collect(Collectors.toMap(
                        PromotionTargetMatcher::type,
                        Function.identity()
                ));
    }

    public PromotionTargetMatcher get(TargetTypeCode type) {

        PromotionTargetMatcher matcher = matchers.get(type);

        if (matcher == null) {
            throw new IllegalStateException(
                    "No PromotionTargetMatcher found for type: " + type
            );
        }

        return matcher;
    }

}
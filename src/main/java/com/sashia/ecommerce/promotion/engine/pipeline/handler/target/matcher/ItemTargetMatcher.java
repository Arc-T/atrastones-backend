package com.sashia.ecommerce.promotion.engine.pipeline.handler.target.matcher;

import com.sashia.ecommerce.catalog.item.ItemDTO;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.pipeline.handler.target.PromotionTargetMatcher;
import com.sashia.ecommerce.promotion.engine.pipeline.handler.target.TargetMatchResult;
import com.sashia.ecommerce.promotion.target.dto.TargetDTO;
import com.sashia.ecommerce.promotion.target.type.TargetTypeCode;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ItemTargetMatcher implements PromotionTargetMatcher {

    @Override
    public TargetTypeCode type() {
        return TargetTypeCode.CATEGORY;
    }

    @Override
    public TargetMatchResult matches(List<TargetDTO> targets, PromotionContext context) {
        Set<Long> targetIds = targets.stream()
                .map(TargetDTO::targetId)
                .collect(Collectors.toUnmodifiableSet());

        Set<ItemDTO> affectedItems = new LinkedHashSet<>();

        for (ItemDTO item : context.getRequest().items()) {

            if (targetIds.contains(item.id())) {
                affectedItems.add(item);
            }
        }

        return new TargetMatchResult(!affectedItems.isEmpty(), affectedItems);
    }

}
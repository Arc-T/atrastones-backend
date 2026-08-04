package com.sashia.ecommerce.promotion.engine.condition.evaluator;

import com.sashia.ecommerce.catalog.item.dto.ItemId;
import com.sashia.ecommerce.promotion.condition.dto.ConditionDTO;
import com.sashia.ecommerce.promotion.condition.type.ConditionTypeCode;
import com.sashia.ecommerce.promotion.engine.condition.ConditionEvaluator;
import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryConditionEvaluator implements ConditionEvaluator {

    private static final Logger log = LoggerFactory.getLogger(CategoryConditionEvaluator.class);

    @Override
    public ConditionTypeCode type() {
        return ConditionTypeCode.CATEGORY_ID;
    }

    @Override
    public void evaluate(List<ConditionDTO> conditions, PromotionContext context) {

        Set<Long> categoryIds = conditions.stream()
                .map(condition -> Long.parseLong(condition.value()))
                .collect(Collectors.toUnmodifiableSet());

        Set<ItemId> matchedItems = context.getRequest().items().stream()
                .filter(item -> categoryIds.contains(item.categoryId()))
                .map(item -> new ItemId(item.id()))
                .collect(Collectors.toSet());

        log.debug("Matched {} items for categories {}", matchedItems, categoryIds);

        context.keepCandidates(matchedItems);
    }
}
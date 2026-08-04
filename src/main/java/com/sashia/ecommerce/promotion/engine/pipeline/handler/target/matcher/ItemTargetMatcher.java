package com.sashia.ecommerce.promotion.engine.pipeline.handler.target.matcher;

import com.sashia.ecommerce.catalog.item.dto.ItemId;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.pipeline.handler.target.PromotionTargetMatcher;
import com.sashia.ecommerce.promotion.target.dto.TargetDTO;
import com.sashia.ecommerce.promotion.target.type.TargetTypeCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ItemTargetMatcher implements PromotionTargetMatcher {

    private static final Logger log = LoggerFactory.getLogger(ItemTargetMatcher.class);

    @Override
    public TargetTypeCode type() {
        return TargetTypeCode.CATEGORY;
    }

    @Override
    public void match(List<TargetDTO> targets, PromotionContext context) {

        Set<Long> targetIds = targets.stream()
                .map(TargetDTO::targetId)
                .collect(Collectors.toUnmodifiableSet());

        Set<ItemId> matchedItems = context.getRequest().items().stream()
                .filter(item -> targetIds.contains(item.id()))
                .map(item -> new ItemId(item.id()))
                .collect(Collectors.toSet());

        log.debug("{} matched items for {}", matchedItems.size(), targets.size());

        context.keepCandidates(matchedItems);
    }

}
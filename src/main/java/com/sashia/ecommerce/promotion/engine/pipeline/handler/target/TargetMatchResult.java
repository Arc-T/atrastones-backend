package com.sashia.ecommerce.promotion.engine.pipeline.handler.target;

import com.sashia.ecommerce.catalog.item.ItemDTO;

import java.util.LinkedHashSet;
import java.util.Set;

public record TargetMatchResult(
        boolean applicable,
        Set<ItemDTO> affectedItems) {

    public TargetMatchResult(boolean matched) {
        this(matched, new LinkedHashSet<>());
    }

}
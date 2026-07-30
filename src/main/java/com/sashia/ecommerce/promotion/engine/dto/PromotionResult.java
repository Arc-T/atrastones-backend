package com.sashia.ecommerce.promotion.engine.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class PromotionResult {

    private final Map<Long, PricedItem> pricedItems = new LinkedHashMap<>();

    /* ***************************** PRICED ITEMS ***************************** */

    public void addPricedItem(PricedItem item) {
        pricedItems.put(item.getItem().id(), item);
    }

    public PricedItem getPricedItem(Long itemId) {
        return pricedItems.get(itemId);
    }

    public Map<Long, PricedItem> getPricedItems() {
        return Collections.unmodifiableMap(pricedItems);
    }

    @Override
    public String toString() {
        return "PromotionResult{" +
                "pricedItems=" + pricedItems +
                '}';
    }

}
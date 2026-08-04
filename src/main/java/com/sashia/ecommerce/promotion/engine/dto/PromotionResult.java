package com.sashia.ecommerce.promotion.engine.dto;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PromotionResult {

    /**
     * Internal storage only. Keyed by item id so the effect applier can do
     * O(1) lookups while mutating prices. Never expose this map directly —
     * callers outside the engine should only ever see {@link #getPricedItems()}.
     */
    private final Map<Long, PricedItem> pricedItemsById;

    private final Map<String, BigDecimal> promotionBenefits;

    public PromotionResult(PromotionRequest request) {
        this.pricedItemsById = new LinkedHashMap<>(request.items().size());

        if (request.order() != null)
            this.promotionBenefits = new LinkedHashMap<>();
        else
            this.promotionBenefits = null;
    }

    /* ***************************** PRICED ITEMS ***************************** */

    private void initializeItemPrices(PromotionRequest request) {
        request.items()
                .forEach(item -> this.pricedItemsById.put(item.id(), new PricedItem(item)));
    }

    public void addPromotionBenefit(String promotionName, BigDecimal promotionBenefit) {
        this.promotionBenefits.put(promotionName, promotionBenefit);
    }

    /**
     * Used internally during effect application, where we already know
     * which item id a {@link com.sashia.ecommerce.promotion.engine.dto.DiscountedItem}
     * refers to and need to mutate it directly.
     */
    public PricedItem getPricedItem(Long itemId) {
        return pricedItemsById.get(itemId);
    }

    /* ********************************** GETTERS ************************************* */

    /**
     * The public-facing result. Ordered the same way the request items were
     * (LinkedHashMap preserves insertion order), and safe to hand to a
     * controller/mapper without leaking the internal keyed structure.
     */
    public List<PricedItem> getPricedItems() {
        return List.copyOf(pricedItemsById.values());
    }

}
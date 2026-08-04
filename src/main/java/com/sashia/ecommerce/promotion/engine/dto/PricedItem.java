package com.sashia.ecommerce.promotion.engine.dto;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PricedItem {

    private final ItemDTO item;

    private final List<AppliedPromotion> promotions = new ArrayList<>();

    public PricedItem(ItemDTO item) {
        this.item = item;
    }

    /* ******************************** HELPERS ******************************** */

    public void addPromotion(AppliedPromotion promotion) {
        promotions.add(promotion);
    }

    /**
     * Returns the current price after applying all promotions.
     *
     * <p>If no promotion has been applied, the item's base price is returned.
     */
    public BigDecimal getCurrentPrice() {

        if (promotions.isEmpty()) {
            return item.basePrice();
        }

        return promotions.getLast().priceAfter();
    }

    /* ******************************** GETTERS ******************************** */

    public ItemDTO getItem() {
        return item;
    }

    public List<AppliedPromotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

    @Override
    public String toString() {
        return "PricedItem{" +
                "item=" + item +
                ", promotions=" + promotions +
                '}';
    }

}
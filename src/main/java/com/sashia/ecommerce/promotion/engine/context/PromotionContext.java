package com.sashia.ecommerce.promotion.engine.context;

import com.sashia.ecommerce.catalog.item.ItemDTO;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.PromotionRequest;
import com.sashia.ecommerce.promotion.engine.effect.PromotionEffect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Mutable context shared by all components while evaluating a single promotion.
 *
 * <p>Each promotion receives its own {@code PromotionContext}. Handlers,
 * evaluators and strategies exchange information through this object during
 * the promotion pipeline.
 */
public class PromotionContext {

    private final PromotionDTO promotion;
    private final PromotionRequest request;

    /**
     * Effects produced by the executed promotion strategy.
     */
    private final List<PromotionEffect> effects = new ArrayList<>();

    /**
     * Items the current promotion is allowed to operate on.
     *
     * <p>These are typically determined by the {@code TargetHandler}.
     */
    private final Set<ItemDTO> applicableItems = new LinkedHashSet<>();

    public PromotionContext(PromotionDTO promotion, PromotionRequest request) {
        this.promotion = promotion;
        this.request = request;
    }

    /* ********************************** EFFECTS *********************************** */

    public void addEffect(PromotionEffect effect) {
        effects.add(effect);
    }

    /* ******************************* APPLICABLE ITEMS ****************************** */

    public void addApplicableItem(ItemDTO item) {
        applicableItems.add(item);
    }

    public void addApplicableItems(Collection<? extends ItemDTO> items) {
        applicableItems.addAll(items);
    }

    /**
     * Returns the items this promotion should operate on.
     *
     * <p>If no handler explicitly selected applicable items, the promotion
     * applies to every item contained in the request.
     */
    public Collection<ItemDTO> getApplicableItems() {
        return applicableItems.isEmpty()
                ? request.items()
                : Collections.unmodifiableSet(applicableItems);
    }

    /* *********************************** GETTERS ********************************** */

    public PromotionDTO getPromotion() {
        return promotion;
    }

    public PromotionRequest getRequest() {
        return request;
    }

    public List<PromotionEffect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

}
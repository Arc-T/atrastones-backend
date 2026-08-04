package com.sashia.ecommerce.promotion.engine.context;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.catalog.item.dto.ItemId;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import com.sashia.ecommerce.promotion.engine.effect.PromotionEffect;

import java.util.*;

public class PromotionContext {

    /**
     * The promotion currently being evaluated.
     */
    private final PromotionDTO promotion;

    /**
     * Original request passed into the promotion engine.
     */
    private final PromotionRequest request;

    /**
     * Candidate items that are still eligible for the current promotion.
     *
     * <p>The candidate set is initialized with every requested item and is
     * progressively narrowed by target matchers and condition evaluators.
     */
    private final Set<ItemId> candidateItems = new LinkedHashSet<>();

    /**
     * Effects produced while executing the promotion strategy.
     *
     * <p>These effects are later consumed by the {@code PromotionEffectApplier}
     * to update the final {@code PromotionResult}.
     */
    private final List<PromotionEffect> effects = new ArrayList<>();

    public PromotionContext(PromotionDTO promotion, PromotionRequest request) {
        this.promotion = promotion;
        this.request = request;
        initialCandidateItems(request.items());
    }

    /* *********************************** HELPERS ************************************ */

    private void initialCandidateItems(List<ItemDTO> items) {
        items.stream()
                .map(ItemDTO::id)
                .map(ItemId::new)
                .forEach(candidateItems::add);
    }

    public Set<ItemId> getCandidateItems() {
        return Collections.unmodifiableSet(candidateItems);
    }

    public List<ItemDTO> getApplicableItems() {
        return request.items().stream()
                .filter(item -> candidateItems.contains(new ItemId(item.id())))
                .toList();
    }

    /**
     * Intersects the current candidate set with the supplied items.
     *
     * <p>This is the primary operation used by target matchers and condition
     * evaluators to progressively reduce the eligible items.
     */
    public void keepCandidates(Collection<ItemId> candidates) {
        candidateItems.retainAll(candidates);
    }

    public void removeCandidateItem(ItemId itemId) {
        candidateItems.remove(itemId);
    }

    public void clearCandidateItems() {
        candidateItems.clear();
    }

    public boolean hasCandidateItems() {
        return !candidateItems.isEmpty();
    }

    public void addEffect(PromotionEffect effect) {
        effects.add(effect);
    }

    public List<PromotionEffect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    /* *********************************** GETTERS *********************************** */

    public PromotionDTO getPromotion() {
        return promotion;
    }

    public PromotionRequest getRequest() {
        return request;
    }

}
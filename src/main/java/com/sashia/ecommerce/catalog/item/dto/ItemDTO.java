package com.sashia.ecommerce.catalog.item.dto;

import com.sashia.ecommerce.promotion.engine.dto.AppliedPromotion;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemDTO {

    private final Long id;

    private final ItemType type;

    private final String title;

    private final Integer quantity;

    private final Long categoryId;

    private final BigDecimal basePrice;

    // Mutable attributes

    @Nullable
    private BigDecimal discountedPrice;

    @Nullable
    private BigDecimal discountAmount;

    @Nullable
    private BigDecimal subtotal;

    private List<AppliedPromotion> promotions = new ArrayList<>();

    /* ****************************** CONSTRUCTORS ************************************ */

    public ItemDTO(Long id, ItemType type, String title, Integer quantity, BigDecimal basePrice, Long categoryId) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.categoryId = categoryId;
    }

    /* ********************************** HELPERS ************************************* */

    public @Nullable BigDecimal discountAmount() {
        return discountAmount;
    }

    public @Nullable BigDecimal discountedPrice() {
        calculateDiscountedPrice();
        return discountedPrice != null ? discountedPrice : null;
    }

    public boolean hasPromotion() {
        return promotions().isEmpty();
    }

    private void calculateDiscountedPrice() {
        if (!promotions.isEmpty()) {

            setDiscountedPrice(promotions.stream()
                    .map(AppliedPromotion::discountAmount)
                    .reduce(basePrice(), BigDecimal::subtract)
            );

            setDiscountAmount(promotions.stream()
                    .map(AppliedPromotion::discountAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
        }
    }

    private void setDiscountedPrice(@NonNull BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    private void setDiscountAmount(@NonNull BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    /* ****************************** GETTER & SETTERS ******************************** */

    public Long id() {
        return id;
    }

    public ItemType type() {
        return type;
    }

    public String title() {
        return title;
    }

    public Integer quantity() {
        return quantity;
    }

    public BigDecimal basePrice() {
        return basePrice;
    }

    public Long categoryId() {
        return categoryId;
    }

    public List<AppliedPromotion> promotions() {
        return promotions;
    }

    public void setPromotions(List<AppliedPromotion> promotions) {
        this.promotions = promotions;
    }

}

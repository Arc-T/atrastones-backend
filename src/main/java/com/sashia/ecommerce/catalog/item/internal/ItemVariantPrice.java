package com.sashia.ecommerce.catalog.item.internal;

import com.sashia.ecommerce.catalog.item.Item;
import com.sashia.ecommerce.catalog.item.ItemVariant;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_variant_prices", schema = "catalog")
public class ItemVariantPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal basePrice;

    private boolean isActive;

    private LocalDateTime createdAt;

    /* **************************** TABLE RELATIONS **********************************/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Item item;

    @OneToOne(mappedBy = "itemVariantPrice")
    private ItemVariant itemVariant;

    /* **************************** GETTER & SETTERS **********************************/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ItemVariant getItemVariant() {
        return itemVariant;
    }

    public void setItemVariant(ItemVariant itemVariant) {
        this.itemVariant = itemVariant;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}

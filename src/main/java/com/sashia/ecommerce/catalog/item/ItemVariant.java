package com.sashia.ecommerce.catalog.item;

import com.sashia.ecommerce.catalog.item.internal.ItemVariantAttributeValue;
import com.sashia.ecommerce.catalog.item.internal.ItemVariantPrice;
import com.sashia.ecommerce.catalog.item.internal.ItemVariantStatusType;
import com.sashia.ecommerce.ordering.order.OrderItem;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item_variants", schema = "catalog")
public class ItemVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;

    @Column(name = "item_variant_price_id", insertable = false, updatable = false)
    private Long itemVariantPriceId;

    @Enumerated(EnumType.STRING)
    private ItemVariantStatusType status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete(strategy = SoftDeleteType.TIMESTAMP)
    private LocalDateTime deletedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Item item;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private ItemVariantPrice itemVariantPrice;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemVariant")
    private Set<ItemVariantAttributeValue> itemVariantAttributeValues = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemVariant")
    private Set<OrderItem> orderItems = new HashSet<>();

    /* ****************************** GETTER & SETTERS ******************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemVariantPriceId() {
        return itemVariantPriceId;
    }

    public void setItemVariantPriceId(Long itemVariantPriceId) {
        this.itemVariantPriceId = itemVariantPriceId;
    }

    public ItemVariantStatusType getStatus() {
        return status;
    }

    public void setStatus(ItemVariantStatusType status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemVariantPrice getItemVariantPrice() {
        return itemVariantPrice;
    }

    public void setItemVariantPrice(ItemVariantPrice itemVariantPrice) {
        this.itemVariantPrice = itemVariantPrice;
    }

    public Set<ItemVariantAttributeValue> getItemVariantAttributeValues() {
        return itemVariantAttributeValues;
    }

    public void setItemVariantAttributeValues(Set<ItemVariantAttributeValue> itemVariantAttributeValues) {
        this.itemVariantAttributeValues = itemVariantAttributeValues;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
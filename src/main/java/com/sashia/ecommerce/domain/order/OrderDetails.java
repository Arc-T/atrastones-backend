package com.sashia.ecommerce.domain.order;

import com.sashia.ecommerce.domain.discount.Discount;
import com.sashia.ecommerce.domain.order.common.ItemType;
import com.sashia.ecommerce.domain.order.common.OrderDetailStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_details", schema = "order")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Long itemId;

    private Integer quantity;

    private Integer unitPrice;

    private Integer discountAmount;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderDetailStatus status;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* ******************************** FOREIGN-KEY RELATIONS **************************************/

    @ManyToOne
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Discount discount;

    /* **************************** GETTER & SETTERS **********************************/

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemType itemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Long itemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer quantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer unitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer discountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long totalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderDetailStatus status() {
        return status;
    }

    public void setStatus(OrderDetailStatus status) {
        this.status = status;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Order order() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Discount discount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

}
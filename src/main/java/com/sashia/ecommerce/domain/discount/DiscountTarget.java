package com.sashia.ecommerce.domain.discount;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount_targets", schema = "promotion")
public class DiscountTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountTargetType targetType;

    private Long targetId;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    /* ******************************** FOREIGN-KEY RELATIONS **************************************/

    public Long id() {
        return id;
    }

    /* **************************** GETTER & SETTERS **********************************/

    public void setId(Long id) {
        this.id = id;
    }

    public DiscountTargetType targetType() {
        return targetType;
    }

    public void setTargetType(DiscountTargetType targetType) {
        this.targetType = targetType;
    }

    public Long targetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Discount discount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public enum DiscountTargetType {
        PRODUCT, CATEGORY, COLLECTION, TAG, CUSTOMER, CUSTOMER_SEGMENT, DELIVERY_METHOD
    }

}

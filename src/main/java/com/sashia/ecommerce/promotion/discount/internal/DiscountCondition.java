package com.sashia.ecommerce.promotion.discount.internal;

import com.sashia.ecommerce.promotion.discount.Discount;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount_conditions", schema = "promotion")
public class DiscountCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conditionGroup;

    @Enumerated(EnumType.STRING)
    private DiscountConditionType type;

    @Enumerated(EnumType.STRING)
    private DiscountOperator operator;

    private String firstValue;

    private String secondValue;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Discount discount;

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /* ******************************** FOREIGN-KEY RELATIONS **************************************/

    public String conditionGroup() {
        return conditionGroup;
    }

    /* **************************** GETTER & SETTERS **********************************/

    public void setConditionGroup(String conditionGroup) {
        this.conditionGroup = conditionGroup;
    }

    public DiscountConditionType type() {
        return type;
    }

    public void setType(DiscountConditionType type) {
        this.type = type;
    }

    public DiscountOperator operator() {
        return operator;
    }

    public void setOperator(DiscountOperator operator) {
        this.operator = operator;
    }

    public String firstValue() {
        return firstValue;
    }

    public void setFirstValue(String firstValue) {
        this.firstValue = firstValue;
    }

    public String secondValue() {
        return secondValue;
    }

    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
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

    public enum LogicalOperator {
        AND, OR
    }

    public enum DiscountConditionType {
        MIN_ORDER_AMOUNT, CUSTOMER_SEGMENT, PRODUCT_CATEGORY
    }

    public enum DiscountOperator {
        EQ, NEQ, GT, GTE, LT, LTE, IN, NOT_IN, BETWEEN, CONTAINS
    }

}

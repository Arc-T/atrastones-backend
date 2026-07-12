package com.sashia.ecommerce.domain.discount;

import com.sashia.ecommerce.domain.order.Order;
import com.sashia.ecommerce.domain.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount_usage", schema = "promotion")
class DiscountUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String discountableType;

    private Long discountableId;

    private BigDecimal OrderAmountBefore;

    private BigDecimal DiscountAmount;

    private BigDecimal OrderAmountAfter;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* ******************************** FOREIGN-KEY RELATIONS **************************************/

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    /* **************************** GETTER & SETTERS **********************************/

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String discountableType() {
        return discountableType;
    }

    public void setDiscountableType(String discountableType) {
        this.discountableType = discountableType;
    }

    public Long discountableId() {
        return discountableId;
    }

    public void setDiscountableId(Long discountableId) {
        this.discountableId = discountableId;
    }

    public BigDecimal OrderAmountBefore() {
        return OrderAmountBefore;
    }

    public void setOrderAmountBefore(BigDecimal orderAmountBefore) {
        OrderAmountBefore = orderAmountBefore;
    }

    public BigDecimal DiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        DiscountAmount = discountAmount;
    }

    public BigDecimal OrderAmountAfter() {
        return OrderAmountAfter;
    }

    public void setOrderAmountAfter(BigDecimal orderAmountAfter) {
        OrderAmountAfter = orderAmountAfter;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User user() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order order() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}

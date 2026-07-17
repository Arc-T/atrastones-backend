package com.sashia.ecommerce.promotion.discount.internal;

import com.sashia.ecommerce.promotion.discount.Discount;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount_scopes", schema = "promotion")
public class DiscountScope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountScopeType code;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToOne(mappedBy = "discountScope")
    private Discount discount;

    /* ******************************** TABLE RELATIONS **************************************/

    public Long id() {
        return id;
    }

    /* **************************** GETTER & SETTERS **********************************/

    public void setId(Long id) {
        this.id = id;
    }

    public DiscountScopeType code() {
        return code;
    }

    public void setCode(DiscountScopeType code) {
        this.code = code;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Discount discount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
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

    public enum DiscountScopeType {
        ORDER, PRODUCT, CATEGORY, DELIVERY, USER
    }

}

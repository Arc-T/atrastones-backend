package com.sashia.ecommerce.domain.discount;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount_types", schema = "promotion")
public class DiscountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountTypeType code;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToOne(mappedBy = "discountType")
    private Discount discount;

    /* ******************************** TABLE RELATIONS **************************************/

    public Long id() {
        return id;
    }

    /* **************************** GETTER & SETTERS **********************************/

    public void setId(Long id) {
        this.id = id;
    }

    public DiscountTypeType code() {
        return code;
    }

    public void setCode(DiscountTypeType code) {
        this.code = code;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Discount discount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public enum DiscountTypeType {
        FIXED, UNIT, PERCENT, BUY_X_GET_Y
    }

}

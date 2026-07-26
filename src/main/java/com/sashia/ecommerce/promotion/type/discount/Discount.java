package com.sashia.ecommerce.promotion.type.discount;

import com.sashia.ecommerce.promotion.Promotion;
import com.sashia.ecommerce.promotion.type.discount.type.DiscountType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts", schema = "promotion")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DiscountType type;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Promotion promotion;

    /* ******************************* TABLE RELATIONS ******************************** */


    /* ****************************** GETTER & SETTERS ******************************** */

}

package com.sashia.ecommerce.ordering.order;

import com.sashia.ecommerce.catalog.item.ItemVariant;
import com.sashia.ecommerce.promotion.Promotion;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_details", schema = "ordering")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderChargeType chargeType;

    private Long chargeTypeId;

    private String chargeTypeName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;

    private BigDecimal taxRate;

    private BigDecimal taxAmount;

    private BigDecimal discountAmount;

    private BigDecimal total;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ItemVariant itemVariant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;

    /* ****************************** GETTER & SETTERS ******************************** */


}
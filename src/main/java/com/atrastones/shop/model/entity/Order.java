package com.atrastones.shop.model.entity;

import com.atrastones.shop.type.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /* ******************************** FOREIGN-KEY RELATIONS **************************************/

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    /* ********************************** TABLE RELATIONS *******************************************/

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Payment> payments;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<OrderDetails> details;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Invoice> invoices;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<OrderTransaction> transactions;

//    @OneToMany
//    private Set<OrderRemainingBalance> remainingBalances;

}

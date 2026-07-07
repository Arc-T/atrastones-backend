package com.sashia.ecommerce.domain.order;

import com.sashia.ecommerce.domain.order.common.Invoice;
import com.sashia.ecommerce.domain.order.common.OrderStatusType;
import com.sashia.ecommerce.domain.order.common.OrderTransaction;
import com.sashia.ecommerce.domain.payment.opg.Payment;
import com.sashia.ecommerce.domain.user.User;
import com.sashia.ecommerce.domain.user.address.Address;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
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

    /* **************************** GETTER & SETTERS **********************************/

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal totalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatusType status() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
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

    public User user() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address address() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Payment> payments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<OrderDetails> details() {
        return details;
    }

    public void setDetails(Set<OrderDetails> details) {
        this.details = details;
    }

    public Set<Invoice> invoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<OrderTransaction> transactions() {
        return transactions;
    }

    public void setTransactions(Set<OrderTransaction> transactions) {
        this.transactions = transactions;
    }

}

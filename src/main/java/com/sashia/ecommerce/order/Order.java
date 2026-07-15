package com.sashia.ecommerce.order;

import com.sashia.ecommerce.order.internal.Invoice;
import com.sashia.ecommerce.order.internal.OrderDetails;
import com.sashia.ecommerce.order.internal.OrderTransaction;
import com.sashia.ecommerce.payment.Payment;
import com.sashia.ecommerce.user.User;
import com.sashia.ecommerce.user.address.Address;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders", schema = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Address address;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderDetails> details = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderTransaction> transactions = new HashSet<>();

    /* ****************************** GETTER & SETTERS ******************************** */

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

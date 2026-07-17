package com.sashia.ecommerce.ordering.order;

import com.sashia.ecommerce.billing.invoice.Invoice;
import com.sashia.ecommerce.billing.payment.Payment;
import com.sashia.ecommerce.catalog.item.ItemDeliveryOption;
import com.sashia.ecommerce.catalog.item.ItemType;
import com.sashia.ecommerce.identity.user.User;
import com.sashia.ecommerce.ordering.order.dto.OrderStatusType;
import com.sashia.ecommerce.ordering.order.internal.OrderTransaction;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders", schema = "ordering")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "item_delivery_option_id", insertable = false, updatable = false)
    private Long itemDeliveryOptionId;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private String orderNumber;

    @Embedded
    private Pricing pricing;

    @Embedded
    private DeliveryDetails delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatusType status;

    private String userNote;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ItemDeliveryOption itemDeliveryOption;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderItem> items = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderTransaction> transactions = new HashSet<>();

    /* ****************************** GETTER & SETTERS ******************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemDeliveryOptionId() {
        return itemDeliveryOptionId;
    }

    public void setItemDeliveryOptionId(Long itemDeliveryOptionId) {
        this.itemDeliveryOptionId = itemDeliveryOptionId;
    }

    public ItemType getOrderItemTypes() {
        return itemType;
    }

    public void setOrderItemTypes(ItemType orderItemTypes) {
        this.itemType = orderItemTypes;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public DeliveryDetails getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryDetails delivery) {
        this.delivery = delivery;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItemDeliveryOption getItemDeliveryOption() {
        return itemDeliveryOption;
    }

    public void setItemDeliveryOption(ItemDeliveryOption itemDeliveryOption) {
        this.itemDeliveryOption = itemDeliveryOption;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<OrderTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<OrderTransaction> transactions) {
        this.transactions = transactions;
    }

}
package com.atrastones.ecommerce.discount;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String discountableType;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private SelectionType selectionType;

    private LocalDateTime startDate;

    private LocalDateTime expireDate;

    private Integer maxUses;

    private Integer usedCount;

    private Boolean isActive;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete
    private LocalDateTime deletedAt;

    public enum SelectionType {
        ALL, INCLUDED, EXCLUDED
    }

    /* ******************************** FOREIGN-KEY RELATIONS **************************************/

    @OneToOne(fetch = FetchType.LAZY)
    private DiscountScope discountScope;

    @OneToOne(fetch = FetchType.LAZY)
    private DiscountType discountType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discount")
    private Set<DiscountTarget> discountTargets;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discount")
    private Set<DiscountCondition> discountConditions;

    /* **************************** GETTER & SETTERS **********************************/

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String discountableType() {
        return discountableType;
    }

    public void setDiscountableType(String discountableType) {
        this.discountableType = discountableType;
    }

    public BigDecimal amount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime startDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime expireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Integer maxUses() {
        return maxUses;
    }

    public void setMaxUses(Integer maxUses) {
        this.maxUses = maxUses;
    }

    public Integer usedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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

    public LocalDateTime deletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public DiscountScope discountScope() {
        return discountScope;
    }

    public void setDiscountScope(DiscountScope discountScope) {
        this.discountScope = discountScope;
    }

    public DiscountType discountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public SelectionType selectionType() {
        return selectionType;
    }

    public Set<DiscountTarget> discountTargets() {
        return discountTargets;
    }

    public void setDiscountTargets(Set<DiscountTarget> discountTargets) {
        this.discountTargets = discountTargets;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public Set<DiscountCondition> discountConditions() {
        return discountConditions;
    }

    public void setDiscountConditions(Set<DiscountCondition> discountConditions) {
        this.discountConditions = discountConditions;
    }

}

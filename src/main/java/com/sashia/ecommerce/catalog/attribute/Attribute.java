package com.sashia.ecommerce.catalog.attribute;

import com.sashia.ecommerce.catalog.attribute.dto.AttributeType;
import com.sashia.ecommerce.catalog.attribute.internal.AttributeValue;
import com.sashia.ecommerce.catalog.category.Category;
import com.sashia.ecommerce.catalog.item.ItemType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attributes", schema = "catalog")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    private Boolean isFilterable;

    @Enumerated(EnumType.STRING)
    private AttributeType type;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ItemType itemType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AttributeValue> attributeValues = new HashSet<>();

    /* ********************************** HELPERS ************************************* */

    public void addAttributeValue(AttributeValue value) {
        value.setAttribute(this);
        attributeValues.add(value);
    }

    public void clearAttributeValues() {
        attributeValues.clear();
    }

    /* ****************************** GETTER & SETTERS ******************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getFilterable() {
        return isFilterable;
    }

    public void setFilterable(Boolean filterable) {
        isFilterable = filterable;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
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

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(Set<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

}
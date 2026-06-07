package com.atrastones.ecommerce.attribute;

import com.atrastones.ecommerce.category.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    private Boolean isFilterable;

    private String type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* **************************** FOREIGN-KEY RELATIONS **********************************/

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    /* ******************************* TABLE RELATIONS ************************************/

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute")
    private Set<AttributeValuePivot> attributeValuesPivot;

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

    public Long categoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean isFilterable() {
        return isFilterable;
    }

    public void setFilterable(Boolean filterable) {
        isFilterable = filterable;
    }

    public String type() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Category category() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<AttributeValuePivot> attributeValuesPivot() {
        return attributeValuesPivot;
    }

    public void setAttributeValuesPivot(Set<AttributeValuePivot> attributeValuesPivot) {
        this.attributeValuesPivot = attributeValuesPivot;
    }

}

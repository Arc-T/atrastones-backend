package com.atrastones.ecommerce.product;

import com.atrastones.ecommerce.category.Category;
import com.atrastones.ecommerce.product.media.ProductMedia;
import com.atrastones.ecommerce.service.group.ServiceGroup;
import com.atrastones.ecommerce.shop.Shop;
import com.atrastones.ecommerce.tag.Tag;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    @Column(name = "shop_id", insertable = false, updatable = false)
    private Long shopId;

    private Integer quantity;

    @Column(name = "service_group_id", insertable = false, updatable = false)
    private Long serviceGroupId;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete
    private LocalDateTime deletedAt;

    /* **************************** FOREIGN-KEY RELATIONS ********************************* */

    @ManyToOne(fetch = FetchType.LAZY)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private ServiceGroup serviceGroup;

    /* **************************** TABLE RELATIONS ********************************* */

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductPrice> prices;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductMedia> media;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductAttributeValue> attributeValues;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductStat> stats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductReview> reviews;

    /* **************************** GETTER & SETTERS ********************************* */

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

    public Long shopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer quantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long serviceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(Long serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStatus status() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
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

    public Shop shop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Category category() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ServiceGroup serviceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public Set<Tag> tags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<ProductMedia> media() {
        return media;
    }

    public void setMedia(Set<ProductMedia> media) {
        this.media = media;
    }

    public Set<ProductAttributeValue> attributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(Set<ProductAttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public Set<ProductStat> stats() {
        return stats;
    }

    public void setStats(Set<ProductStat> stats) {
        this.stats = stats;
    }

    public Set<ProductReview> reviews() {
        return reviews;
    }

    public void setReviews(Set<ProductReview> reviews) {
        this.reviews = reviews;
    }

    public Set<ProductPrice> prices() {
        return prices;
    }

    public void setPrices(Set<ProductPrice> prices) {
        this.prices = prices;
    }

}

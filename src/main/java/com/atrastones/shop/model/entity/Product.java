package com.atrastones.shop.model.entity;

import com.atrastones.shop.type.ProductStatus;
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

    private BigDecimal price;

    @Column(name = "service_group_id", insertable = false, updatable = false)
    private Long serviceGroupId;

    private String description;

    @Column(name = "discount_amount")
    private Long discountAmount;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /* **************************** FOREIGN-KEY RELATIONS **********************************/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_group_id")
    private ServiceGroup serviceGroup;

    /* **************************** TABLE RELATIONS **********************************/

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<ProductMedia> media;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductAttributeValue> attributeValues;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductStat> stats;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductReview> reviews;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<OrderDetails> orderDetails;

}

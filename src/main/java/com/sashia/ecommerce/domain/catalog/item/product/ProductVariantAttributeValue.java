package com.sashia.ecommerce.domain.catalog.item.product;

import com.sashia.ecommerce.domain.catalog.attribute.Attribute;
import com.sashia.ecommerce.domain.catalog.attribute.AttributeValue;
import com.sashia.ecommerce.domain.catalog.item.product.variant.ProductVariant;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_variant_attribute_values")
public class ProductVariantAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* **************************** FOREIGN-KEY RELATIONS **********************************/

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attribute attribute;

    @ManyToOne(fetch = FetchType.LAZY)
    private AttributeValue attributeValue;

    /* **************************** GETTER & SETTERS **********************************/

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public Attribute attribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public AttributeValue attributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }

}

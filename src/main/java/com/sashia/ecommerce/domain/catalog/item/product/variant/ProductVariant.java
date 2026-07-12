package com.sashia.ecommerce.domain.catalog.item.product.variant;

import com.sashia.ecommerce.domain.catalog.item.product.VariantPrice;
import com.sashia.ecommerce.domain.catalog.item.product.Product;
import com.sashia.ecommerce.domain.catalog.item.product.ProductStatus;
import com.sashia.ecommerce.domain.catalog.item.product.ProductVariantAttributeValue;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_variants", schema = "catalog")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant")
    private Set<VariantPrice> variantPrices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant")
    private Set<ProductVariantAttributeValue> attributeValues = new HashSet<>();

    /* ****************************** GETTER & SETTERS ******************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<ProductVariantAttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(Set<ProductVariantAttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public Set<VariantPrice> getPrices() {
        return variantPrices;
    }

    public void setPrices(Set<VariantPrice> variantPrices) {
        this.variantPrices = variantPrices;
    }

}

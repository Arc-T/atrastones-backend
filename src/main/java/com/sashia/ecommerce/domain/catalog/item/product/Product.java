package com.sashia.ecommerce.domain.catalog.item.product;

import com.sashia.ecommerce.domain.catalog.item.Item;
import com.sashia.ecommerce.domain.catalog.item.product.variant.ProductVariant;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products", schema = "catalog")
public class Product {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Item item;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductVariant> productVariants = new HashSet<>();

    /* **************************** GETTER & SETTERS ********************************* */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getCatalogItem() {
        return item;
    }

    public void setCatalogItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


}

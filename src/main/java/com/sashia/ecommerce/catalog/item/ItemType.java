package com.sashia.ecommerce.catalog.item;

import com.sashia.ecommerce.catalog.attribute.Attribute;
import com.sashia.ecommerce.catalog.category.Category;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item_types", schema = "catalog")
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CatalogItemType catalogItemType;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemType")
    private Set<Category> categories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemType")
    private Set<Attribute> attributes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemType")
    private Set<Item> items = new HashSet<>();

    /* ****************************** GETTER & SETTERS ******************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatalogItemType getCatalogItemType() {
        return catalogItemType;
    }

    public void setCatalogItemType(CatalogItemType catalogItemType) {
        this.catalogItemType = catalogItemType;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

}
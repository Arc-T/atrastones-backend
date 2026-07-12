package com.sashia.ecommerce.domain.catalog.item.serviceoffering.group;

import com.sashia.ecommerce.domain.catalog.item.product.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "service_groups")
public class ServiceGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* **************************** TABLE RELATIONS **********************************/

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Product> products;

    /* **************************** GETTER & SETTERS *********************************/

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

    public Set<Product> products() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}

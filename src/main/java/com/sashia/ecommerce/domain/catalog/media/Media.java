package com.sashia.ecommerce.domain.catalog.media;

import com.sashia.ecommerce.domain.catalog.item.product.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private Integer displayOrder;

    private String extension;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* **************************** FOREIGN-KEY RELATIONS **********************************/

    @ManyToOne
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    private MediaType mediaType;

    /* **************************** GETTER & SETTERS **********************************/

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String url() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer displayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String extension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Product product() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public MediaType mediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

}

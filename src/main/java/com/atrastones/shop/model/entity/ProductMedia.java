package com.atrastones.shop.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product_media")
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private Integer displayOrder;

    private String extension;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /* **************************** FOREIGN-KEY RELATIONS **********************************/

    @ManyToOne
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    private MediaType mediaType;

}

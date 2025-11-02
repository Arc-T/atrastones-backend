package com.atrastones.shop.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product_stats")
public class ProductStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer views;

    private Integer likes;

    private Integer shares;

    private Integer comments;

    private Short ratings;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

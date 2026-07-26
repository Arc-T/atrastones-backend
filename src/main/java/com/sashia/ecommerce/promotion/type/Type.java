package com.sashia.ecommerce.promotion.type;

import com.sashia.ecommerce.promotion.Promotion;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "types", schema = "promotion")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeCode code;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private Set<Promotion> promotions = new HashSet<>();

}
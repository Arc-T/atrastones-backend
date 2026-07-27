package com.sashia.ecommerce.promotion.target.type;

import com.sashia.ecommerce.promotion.Promotion;
import com.sashia.ecommerce.promotion.target.Target;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "target_types", schema = "promotion")
public class TargetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TargetTypeCode code;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "targetType")
    private Set<Promotion> promotion = new LinkedHashSet<>();

    /* ****************************** GETTER & SETTERS ******************************** */

}

package com.sashia.ecommerce.promotion;

import com.sashia.ecommerce.promotion.condition.Condition;
import com.sashia.ecommerce.promotion.scope.Scope;
import com.sashia.ecommerce.promotion.target.Target;
import com.sashia.ecommerce.promotion.target.type.TargetType;
import com.sashia.ecommerce.promotion.type.Type;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "promotions", schema = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer priority;

    private boolean isActive;

    private boolean isStackable;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @SoftDelete(strategy = SoftDeleteType.TIMESTAMP)
    private LocalDateTime deletedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Scope scope;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TargetType targetType;

    /* ******************************* TABLE RELATIONS ******************************** */

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private Set<Target> targets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private Set<Condition> conditions = new LinkedHashSet<>();

}
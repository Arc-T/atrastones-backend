package com.sashia.ecommerce.promotion.condition.type;

import com.sashia.ecommerce.promotion.scope.Scope;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "condition_types", schema = "promotion")
public class ConditionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ConditionTypeCode code;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* ******************************* TABLE RELATIONS ******************************** */

    @ManyToMany(mappedBy = "conditionTypes")
    private Set<Scope> levels = new LinkedHashSet<>();

    /* ********************************** HELPERS ************************************* */

    /* ****************************** GETTER & SETTERS ******************************** */

}

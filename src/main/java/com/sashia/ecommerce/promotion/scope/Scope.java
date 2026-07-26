package com.sashia.ecommerce.promotion.scope;

import com.sashia.ecommerce.promotion.condition.type.ConditionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

//What is being evaluated?

@Entity
@Table(name = "promotion_scopes", schema = "promotion")
public class Scope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ScopeCode code;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* ******************************* TABLE RELATIONS ******************************** */

    @ManyToMany
    @JoinTable(
            name = "promotion_level_condition_types",
            schema = "promotion",
            joinColumns = @JoinColumn(name = "promotion_level_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_condition_type_id")
    )
    private Set<ConditionType> conditionTypes = new LinkedHashSet<>();

    /* ********************************** HELPERS ************************************* */

    public void addConditionType(ConditionType conditionType) {
        conditionTypes.add(conditionType);
//        conditionType.getLevels().add(this);
    }

    public void removeConditionType(ConditionType conditionType) {
        conditionTypes.remove(conditionType);
//        conditionType.getLevels().remove(this);
    }

    /* ****************************** GETTER & SETTERS ******************************** */

}
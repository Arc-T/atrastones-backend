package com.sashia.ecommerce.promotion.condition;

import com.sashia.ecommerce.promotion.Promotion;
import com.sashia.ecommerce.promotion.condition.operator.ConditionOperator;
import com.sashia.ecommerce.promotion.condition.type.ConditionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "conditions", schema = "promotion")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conditionValue;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /* **************************** FOREIGN-KEY RELATIONS ***************************** */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ConditionType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ConditionOperator operator;

}
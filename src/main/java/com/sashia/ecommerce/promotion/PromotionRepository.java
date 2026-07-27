package com.sashia.ecommerce.promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("""
            SELECT p FROM Promotion p WHERE p.isActive = TRUE
            """)
    List<Promotion> findAllActivePromotions();

}

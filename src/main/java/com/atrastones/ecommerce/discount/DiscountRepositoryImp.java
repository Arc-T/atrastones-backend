package com.atrastones.ecommerce.discount;

import com.atrastones.ecommerce.discount.common.DiscountCreateDTO;
import com.atrastones.ecommerce.discount.common.DiscountEditDTO;
import com.atrastones.infrastructure.db.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiscountRepositoryImp implements DiscountRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public DiscountRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Long save(DiscountCreateDTO discount) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO discounts (name, discountable_type, discount_type_id, discount_scope_id, amount,
                                                       selection_type, start_date, expire_date, max_uses, description)
                                       VALUES (:name, :discountable_type, :discount_type_id, :discount_scope_id, :amount,
                                                       :selection_type, :start_date, :expire_date, :max_uses, :description)
                                """)
                        .param("name", discount.name())
                        .param("discountable_type", discount.discountableType())
                        .param("discount_type_id", discount.typeId())
                        .param("discount_scope_id", discount.scopeId())
                        .param("amount", discount.amount())
                        .param("selection_type", discount.selectionType())
                        .param("start_date", discount.startDate())
                        .param("expire_date", discount.expireDate())
                        .param("max_uses", discount.maxUses())
                        .param("description", discount.description())
        );
    }

    @Override
    public Long[] saveIncludedTarget(Long discountId, List<Long> targetIds) {
//        return JdbcUtils.insertBatch(
//                jdbcClient.sql("INSERT INTO discount_targets (target_type, target_id) VALUES (:target_type, :target_id)")
//                        .param("target_type", "")
//                        .param("target_id", targetIds)
//        );
        return null;
    }

    @Override
    public Optional<Discount> get(Long id) {
        return Optional.ofNullable(entityManager.createQuery("""
                        SELECT d FROM Discount d WHERE id = :id
                        """, Discount.class)
                .setParameter("id", id)
                .getSingleResultOrNull()
        );
    }

    @Override
    public Optional<Discount> getActiveDiscount() {
        return Optional.ofNullable(entityManager.createQuery("""
                        SELECT DISTINCT d FROM Discount d
                               JOIN FETCH d.discountScope
                               JOIN FETCH d.discountType
                               LEFT JOIN FETCH d.discountConditions
                               LEFT JOIN FETCH d.discountTargets
                        WHERE d.isActive = TRUE
                              AND d.startDate <= CURRENT_TIMESTAMP
                              AND (d.expireDate IS NULL OR d.expireDate > CURRENT_TIMESTAMP)
                        ORDER BY d.id DESC LIMIT 1
                        """, Discount.class)
                .getSingleResultOrNull());
    }

    @Override
    public List<Discount> getApplicableDiscountsForProduct(Long productId) {
        return entityManager.createQuery("""
                        SELECT DISTINCT d FROM Discount d
                               JOIN d.discountTargets t
                        WHERE d.isActive = TRUE
                              AND d.startDate <= CURRENT_TIMESTAMP()
                              AND (d.expireDate IS NULL OR d.expireDate > CURRENT_TIMESTAMP())
                              AND (
                                  (t.targetType = 'PRODUCT' AND t.targetId = :productId) OR
                                  (t.targetType = 'CATEGORY' AND t.targetId = :categoryId)
                              )
                        """, Discount.class)
                .getResultList();
    }

    @Override
    public Page<Discount> getAll(Pageable pageable, DiscountSearchDTO search) {
        return PageableExecutionUtils.getPage(
                entityManager.createQuery("SELECT d FROM Discount d", Discount.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    @Override
    public void update(Long id, DiscountEditDTO discount) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE discounts 
                                SET name = :name, discountable_type = :discountable_type, discount_type_id = :discount_type_id, 
                                    discount_scope_id = :discount_scope_id, amount = :amount, selection_type = :selection_type,
                                    start_date = :start_date, expire_date = :expire_date, max_uses = :max_uses, is_active = :is_active
                                    description = :description
                                WHERE id = :id
                                """)
                        .param("id", id)
                        .param("name", discount.name())
                        .param("discountable_type", discount.discountableType())
                        .param("discount_type_id", discount.typeId())
                        .param("discount_scope_id", discount.scopeId())
                        .param("amount", discount.amount())
                        .param("selection_type", discount.selectionType())
                        .param("start_date", discount.startDate())
                        .param("expire_date", discount.expireDate())
                        .param("max_uses", discount.maxUses())
                        .param("is_active", discount.isActive())
                        .param("description", discount.description())
        );
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Long count() {
        return jdbcClient.sql("SELECT COUNT(*) FROM discounts")
                .query(Long.class)
                .single();
    }

}
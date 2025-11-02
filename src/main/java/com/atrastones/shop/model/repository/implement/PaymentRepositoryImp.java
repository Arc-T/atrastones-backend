package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.PaymentDTO;
import com.atrastones.shop.model.entity.Payment;
import com.atrastones.shop.model.repository.contract.PaymentRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementation of {@link PaymentRepository} using a mix of JDBC and JPA.
 * <p>
 * Handles CRUD operations for payments. Uses {@link JdbcClient} for create and update
 * operations for performance, and {@link EntityManager} for select queries to leverage ORM benefits.
 */
@Repository
public class PaymentRepositoryImp implements PaymentRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public PaymentRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // ----------------------------- CREATE -----------------------------

    @Override
    public Long create(PaymentDTO payment) {

        String INSERT_PAYMENT_SQL = """
                INSERT INTO payments (user_id, order_id, payment_method, amount, status, authority, reference_id, fee_type, fee, description)
                       VALUES (:user_id, :order_id, :payment_method, :amount, :status, :authority, :reference_id, :fee_type, :fee, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_PAYMENT_SQL)
                        .param("user_id", payment.getUserId())
                        .param("order_id", payment.getOrderId())
                        .param("payment_method", payment.getPaymentMethod())
                        .param("amount", payment.getAmount())
                        .param("status", payment.getStatus())
                        .param("authority", payment.getAuthority())
                        .param("reference_id", payment.getReferenceId())
                        .param("fee_type", payment.getFeeType())
                        .param("fee", payment.getFee())
                        .param("description", payment.getDescription())
        );
    }

    // ----------------------------- UPDATE -----------------------------

    @Override
    public void update(Long id, PaymentDTO payment) {

        String UPDATE_PAYMENT_SQL = """
                UPDATE payments
                       SET user_id = :user_id, order_id = :order_id, payment_method = :payment_method,
                           amount = :amount, status = :status, authority = :authority, reference_id = :reference_id, fee_type = :fee_type, fee = :fee, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_PAYMENT_SQL)
                        .param("user_id", payment.getUserId())
                        .param("order_id", payment.getOrderId())
                        .param("payment_method", payment.getPaymentMethod())
                        .param("amount", payment.getAmount())
                        .param("status", payment.getStatus())
                        .param("authority", payment.getAuthority())
                        .param("reference_id", payment.getReferenceId())
                        .param("fee_type", payment.getFeeType())
                        .param("fee", payment.getFee())
                        .param("description", payment.getDescription())
                , "PAYMENT.ID.INVALID"
        );
    }

    // ----------------------------- SELECT -----------------------------

    @Override
    public Optional<Payment> get(Long id) {

        String SELECT_PAYMENT_HQL = """
                SELECT p FROM Payment p
                         JOIN FETCH p.order
                         WHERE p.id = :payment_id
                """;

        return Optional.ofNullable(
                entityManager.createQuery(SELECT_PAYMENT_HQL, Payment.class)
                        .setParameter("payment_id", id)
                        .getSingleResult()
        );
    }


    // ----------------------------- OPERATIONS -----------------------------

    @Override
    public Long count() {

        String COUNT_PAYMENT_SQL = """
                SELECT COUNT(*) FROM payments
                """;

        return jdbcClient.sql(COUNT_PAYMENT_SQL)
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {

        String EXISTS_PAYMENT_SQL = """
                SELECT EXISTS(SELECT 1 FROM payments WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_PAYMENT_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

}

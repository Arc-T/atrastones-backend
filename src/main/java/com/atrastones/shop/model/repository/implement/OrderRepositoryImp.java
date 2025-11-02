package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.OrderDTO;
import com.atrastones.shop.model.entity.Invoice;
import com.atrastones.shop.model.entity.OrderDetails;
import com.atrastones.shop.model.repository.contract.OrderRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of {@link OrderRepository} using a mix of JDBC and JPA.
 * <p>
 * Handles CRUD operations for orders. Uses {@link JdbcClient} for simple operations (create, update)
 * for performance, and {@link EntityManager} for select queries with joins to leverage ORM benefits.
 */
@Repository
public class OrderRepositoryImp implements OrderRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public OrderRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // -------------------------------------- CREATE --------------------------------------

    @Override
    public long create(OrderDTO order) {

        String INSERT_ORDER_SQL = """
                INSERT INTO orders (user_id, address_id, total_price, status, description)
                       VALUES (:user_id, :address_id, :total_price, :status, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_ORDER_SQL)
                        .param("user_id", order.getUserId())
                        .param("address_id", order.getAddressId())
                        .param("total_price", order.getTotalPrice())
                        .param("status", order.getStatus())
                        .param("description", order.getDescription())
        );
    }

    // -------------------------------------- UPDATE --------------------------------------

    @Override
    public void update(long id, OrderDTO order) {

        String UPDATE_ORDER_SQL = """
                UPDATE orders
                       SET user_id = :user_id, address_id = :address_id, total_price = :total_price, status = :status, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_ORDER_SQL)
                        .param("id", id)
                        .param("user_id", order.getUserId())
                        .param("address_id", order.getAddressId())
                        .param("total_price", order.getTotalPrice())
                        .param("status", order.getStatus())
                        .param("description", order.getDescription())
                , "ORDER.ID.INVALID"
        );
    }

    // -------------------------------------- SELECT --------------------------------------

    @Override
    public List<OrderDetails> getOrderDetails(long id) {

        String SELECT_ORDER_DETAILS_HQL = """
                SELECT od FROM OrderDetails od
                          JOIN FETCH od.order
                          WHERE od.order.id = :order_id
                """;

        return entityManager.createQuery(SELECT_ORDER_DETAILS_HQL, OrderDetails.class)
                .setParameter("order_id", id)
                .getResultList();
    }

    @Override
    public List<Invoice> getOrderInvoice(long id) {

        String SELECT_ORDER_INVOICE_HQL = """
                SELECT i FROM Invoice i
                         JOIN FETCH i.order o
                         WHERE o.id = :order_id
                """;

        return entityManager.createQuery(SELECT_ORDER_INVOICE_HQL, Invoice.class)
                .setParameter("order_id", id)
                .getResultList();
    }

    // -------------------------------------- OPERATIONS --------------------------------------

    @Override
    public Long count() {

        String COUNT_ORDER_SQL = """
                SELECT COUNT(*) FROM orders
                """;

        return jdbcClient.sql(COUNT_ORDER_SQL)
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(Long id) {

        String EXISTS_ORDER_SQL = """
                SELECT EXISTS(SELECT 1 FROM orders WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_ORDER_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

}

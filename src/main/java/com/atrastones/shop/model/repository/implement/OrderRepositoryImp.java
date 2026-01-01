package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.OrderDTO;
import com.atrastones.shop.dto.search.OrderSearchDTO;
import com.atrastones.shop.model.entity.Invoice;
import com.atrastones.shop.model.entity.Order;
import com.atrastones.shop.model.entity.OrderDetails;
import com.atrastones.shop.model.repository.contract.OrderRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryImp implements OrderRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public OrderRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // -------------------------------------- CREATE --------------------------------------

    @Override
    public Long create(OrderDTO order) {

        String INSERT_ORDER_SQL = """
                INSERT INTO orders (user_id, address_id, total_price, status, description)
                       VALUES (:user_id, :address_id, :total_price, :status, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_ORDER_SQL)
                        .param("user_id", order.userId())
                        .param("address_id", order.addressId())
                        .param("total_price", order.totalPrice())
                        .param("status", order.status())
                        .param("description", order.description())
        );
    }

    // -------------------------------------- UPDATE --------------------------------------

    @Override
    public void update(Long id, OrderDTO order) {

        String UPDATE_ORDER_SQL = """
                UPDATE orders
                       SET user_id = :user_id, address_id = :address_id, total_price = :total_price, status = :status, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_ORDER_SQL)
                        .param("id", id)
                        .param("user_id", order.userId())
                        .param("address_id", order.addressId())
                        .param("total_price", order.totalPrice())
                        .param("status", order.status())
                        .param("description", order.description())
                , "ORDER.ID.INVALID"
        );
    }

    // -------------------------------------- SELECT --------------------------------------

    @Override
    public Page<Order> findAll(Pageable pageable, OrderSearchDTO search) {

        String SELECT_ORDER_HQL = """
                SELECT o FROM Order o
                """;

        return PageableExecutionUtils.getPage(
                entityManager.createQuery(SELECT_ORDER_HQL, Order.class)
                        .getResultList(),
                pageable,
                this::count
        );
    }

    @Override
    public List<OrderDetails> findOrderDetails(Long id) {

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
    public List<Invoice> findOrderInvoice(Long id) {

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

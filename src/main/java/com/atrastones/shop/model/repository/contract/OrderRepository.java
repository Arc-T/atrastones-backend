package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.OrderDTO;
import com.atrastones.shop.model.entity.Invoice;
import com.atrastones.shop.model.entity.OrderDetails;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface OrderRepository {

    // ----------------------------- CREATE --------------------------------------

    /**
     * Creates a new order and returns its generated ID.
     *
     * @param order the order data to create
     * @return the generated ID of the new order
     * @throws RuntimeException if the insert fails
     */
    long create(OrderDTO order);

    // ----------------------------- UPDATE --------------------------------------

    /**
     * Updates an existing order by ID.
     *
     * @param id    the ID of the order to update
     * @param order the updated order data
     * @throws EntityNotFoundException if no order with the given ID exists
     * @throws DataAccessException        if the update fails
     */
    void update(long id, OrderDTO order);

    // ----------------------------- SELECT --------------------------------------

    /**
     * Retrieves the order details for a given order ID.
     *
     * @param id the ID of the order
     * @return a list of order details
     */
    List<OrderDetails> getOrderDetails(long id);

    /**
     * Retrieves the invoices associated with a given order ID.
     *
     * @param id the ID of the order
     * @return a list of invoices
     */
    List<Invoice> getOrderInvoice(long id);

    // ----------------------------- OPERATIONS --------------------------------------

    /**
     * Counts the total number of orders.
     *
     * @return the total count
     */
    Long count();

    /**
     * Checks if an order exists by ID.
     *
     * @param id the ID to check
     * @return true if exists, false otherwise
     */
    boolean exists(Long id);

}

package com.sashia.ecommerce.domain.order;

import com.sashia.ecommerce.domain.order.common.Invoice;
import com.sashia.ecommerce.domain.order.common.OrderDTO;
import com.sashia.ecommerce.domain.order.common.OrderSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository {

    // ----------------------------- CREATE --------------------------------------

    Long create(OrderDTO order);

    // ----------------------------- UPDATE --------------------------------------

    void update(Long id, OrderDTO order);

    // ----------------------------- SELECT --------------------------------------

    Page<Order> findAll(Pageable pageable, OrderSearchDTO search);

    List<OrderDetails> findOrderDetails(Long id);

    List<Invoice> findOrderInvoice(Long id);

    // ----------------------------- OPERATIONS --------------------------------------

    Long count();

    boolean exists(Long id);

}

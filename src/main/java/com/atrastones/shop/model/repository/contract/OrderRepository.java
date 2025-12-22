package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.search.OrderSearch;
import com.atrastones.shop.dto.OrderDTO;
import com.atrastones.shop.model.entity.Invoice;
import com.atrastones.shop.model.entity.Order;
import com.atrastones.shop.model.entity.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository {

    // ----------------------------- CREATE --------------------------------------

    Long create(OrderDTO order);

    // ----------------------------- UPDATE --------------------------------------

    void update(Long id, OrderDTO order);

    // ----------------------------- SELECT --------------------------------------

    Page<Order> findAllPageable(Pageable pageable, OrderSearch search);

    List<OrderDetails> findOrderDetails(Long id);

    List<Invoice> findOrderInvoice(Long id);

    // ----------------------------- OPERATIONS --------------------------------------

    Long count();

    boolean exists(Long id);

}

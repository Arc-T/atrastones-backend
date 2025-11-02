package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.OrderDTO;

import java.util.Optional;

public interface OrderService {

    /* ******************************** CRUD ******************************** */

    Optional<OrderDTO> get(Long id);

    Long create(OrderDTO order);

    void update(Long id, OrderDTO order);

    /* ******************************** PAGE ******************************** */

    void summary();

}

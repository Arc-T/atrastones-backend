package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.search.OrderSearchDTO;
import com.atrastones.shop.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {

    /* ******************************** CRUD ******************************** */

    Optional<OrderDTO> get(Long id);

    Page<OrderDTO> getAll(Pageable pageable, OrderSearchDTO search);

    Long create(OrderDTO order);

    void update(Long id, OrderDTO order);

    /* ******************************** PAGE ******************************** */

    void summary();

}

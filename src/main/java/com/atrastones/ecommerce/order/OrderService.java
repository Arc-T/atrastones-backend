package com.atrastones.ecommerce.order;

import com.atrastones.ecommerce.order.common.OrderDTO;
import com.atrastones.ecommerce.order.common.OrderSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderService {

    /* ========================== CREATE ========================== */

    Long create(OrderDTO order);

    /* ========================== READ ========================== */

    Optional<OrderDTO> get(Long id);

    /* ========================== UPDATE ========================== */

    Page<OrderDTO> getAll(Pageable pageable, OrderSearchDTO search);

    /* ========================== DELETE ========================== */

    void update(Long id, OrderDTO order);

    /* ========================== OPERATION ========================== */

    boolean exists(Long id);

}

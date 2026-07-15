package com.sashia.ecommerce.order;

import com.sashia.ecommerce.order.dto.OrderDTO;
import com.sashia.ecommerce.order.dto.OrderSearchDTO;
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

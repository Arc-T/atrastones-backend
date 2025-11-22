package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.api.search.OrderSearch;
import com.atrastones.shop.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    /* ******************************** CRUD ******************************** */

    Optional<OrderDTO> get(Long id);

    List<OrderDTO> getAll(OrderSearch search);

    Page<OrderDTO> getAllPaginated(Pageable pageable, OrderSearch search);

    Long create(OrderDTO order);

    void update(Long id, OrderDTO order);

    /* ******************************** PAGE ******************************** */

    void summary();

}

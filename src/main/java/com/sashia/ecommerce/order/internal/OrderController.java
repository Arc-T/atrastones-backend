package com.sashia.ecommerce.order.internal;

import com.sashia.ecommerce.order.OrderService;
import com.sashia.ecommerce.order.dto.OrderDTO;
import com.sashia.ecommerce.order.dto.OrderSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_ORDERS')")
    public ResponseEntity<Page<OrderDTO>> readAll(Pageable pageable, OrderSearchDTO search) {
        return ResponseEntity.ok(orderService.getAll(pageable, search));
    }

}
package com.atrastones.shop.controller;

import com.atrastones.shop.dto.search.OrderSearchDTO;
import com.atrastones.shop.dto.OrderDTO;
import com.atrastones.shop.model.service.contract.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Page<OrderDTO>> readAll(Pageable pageable, OrderSearchDTO search) {
        return ResponseEntity.ok(orderService.getAll(pageable, search));
    }

}
package com.atrastones.shop.api;

import com.atrastones.shop.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> readAll(Pageable pageable) {
        return null;
    }

}

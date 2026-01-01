package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.search.OrderSearchDTO;
import com.atrastones.shop.dto.OrderDTO;
import com.atrastones.shop.model.repository.contract.OrderRepository;
import com.atrastones.shop.model.service.contract.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<OrderDTO> getAll(Pageable pageable, OrderSearchDTO search) {
        return orderRepository.findAll(pageable, search).map(OrderDTO::toDTO);
    }

    @Override
    public Long create(OrderDTO order) {
        return 0L;
    }

    @Override
    public void update(Long id, OrderDTO order) {

    }

    @Override
    public void summary() {

    }

}

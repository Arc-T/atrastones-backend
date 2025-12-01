package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Order;
import com.atrastones.shop.type.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        Long userId,
        Long addressId,
        BigDecimal totalPrice,
        OrderStatus status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // ********************** DTOs **********************
    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getAddress().getId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getDescription(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

}

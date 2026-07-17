package com.sashia.ecommerce.ordering.order.dto;

import com.sashia.ecommerce.ordering.order.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        Long userId,
        BigDecimal totalPrice,
//        OrderStatusType status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // ********************** DTOs **********************
    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUserId(),
                order.getPricing().total(),
//                order.status(),
                order.getDescription(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

}
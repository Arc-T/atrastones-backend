package com.atrastones.ecommerce.order.common;

import com.atrastones.ecommerce.order.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        Long userId,
        Long addressId,
        BigDecimal totalPrice,
        OrderStatusType status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    // ********************** DTOs **********************
    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.id(),
                order.user().id(),
                order.address().id(),
                order.totalPrice(),
                order.status(),
                order.description(),
                order.createdAt(),
                order.updatedAt()
        );
    }

}
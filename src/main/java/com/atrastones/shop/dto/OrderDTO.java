package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Order;
import com.atrastones.shop.type.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class OrderDTO {

    private Long id;

    private Long userId;

    private Long addressId;

    private BigDecimal totalPrice;

    private OrderStatus status;

    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    // ********************** DTOs **********************

    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .addressId(order.getAddress().getId())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .description(order.getDescription())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}

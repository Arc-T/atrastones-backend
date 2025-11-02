package com.atrastones.shop.dto;

import com.atrastones.shop.type.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class OrderDTO {

    private Long id;

    private Long userId;

    private Long addressId;

    private Integer totalPrice;

    private OrderStatus status;

    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}

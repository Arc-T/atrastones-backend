package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Shop;
import com.atrastones.shop.type.ShopStatus;

import java.time.LocalDateTime;

public record ShopDTO(
        Long id,
        String name,
        String phone,
        ShopStatus status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {

    public static ShopDTO toDTO(Shop shop) {
        return new ShopDTO(
                shop.getId(),
                shop.getName(),
                shop.getPhone(),
                shop.getStatus(),
                shop.getDescription(),
                shop.getCreatedAt(),
                null,
                null
        );
    }
}

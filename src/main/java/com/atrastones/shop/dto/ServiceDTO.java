package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ServiceDTO(
        Long id,
        String name,
        BigDecimal cost,
        Long serviceGroupId,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    // ********************** DTOs **********************
    public static ServiceDTO toDTO(Service service) {
        return new ServiceDTO(
                service.getId(),
                service.getName(),
                service.getCost(),
                service.getServiceGroup().getId(),
                service.getDescription(),
                service.getCreatedAt(),
                service.getUpdatedAt(),
                service.getDeletedAt()
        );
    }

}

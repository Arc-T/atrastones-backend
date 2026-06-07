package com.atrastones.ecommerce.service.common;

import com.atrastones.ecommerce.service.Service;

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
                service.id(),
                service.name(),
                service.cost(),
                service.serviceGroupId(),
                service.description(),
                service.createdAt(),
                service.updatedAt(),
                service.deletedAt()
        );
    }

}

package com.sashia.ecommerce.domain.service.group.common;

import com.sashia.ecommerce.domain.service.group.ServiceGroup;

public record ServiceGroupDTO(Long id, String name, String description) {
    // ********************** DTOs **********************
    public static ServiceGroupDTO toDTO(ServiceGroup serviceGroup) {
        return new ServiceGroupDTO(
                serviceGroup.id(),
                serviceGroup.name(),
                serviceGroup.description()
        );
    }
}
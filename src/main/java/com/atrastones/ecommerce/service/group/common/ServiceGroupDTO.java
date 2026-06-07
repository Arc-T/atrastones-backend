package com.atrastones.ecommerce.service.group.common;

import com.atrastones.ecommerce.service.group.ServiceGroup;

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
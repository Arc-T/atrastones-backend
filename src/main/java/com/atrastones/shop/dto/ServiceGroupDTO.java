package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.ServiceGroup;

public record ServiceGroupDTO(Long id, String name, String description) {
    // ********************** DTOs **********************
    public static ServiceGroupDTO toDTO(ServiceGroup serviceGroup) {
        return new ServiceGroupDTO(
                serviceGroup.getId(),
                serviceGroup.getName(),
                serviceGroup.getDescription()
        );
    }
}
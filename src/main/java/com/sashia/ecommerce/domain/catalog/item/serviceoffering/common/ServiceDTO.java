package com.sashia.ecommerce.domain.catalog.item.serviceoffering.common;

import com.sashia.ecommerce.domain.catalog.item.serviceoffering.ServiceOffering;

import java.math.BigDecimal;

public record ServiceDTO(
        BigDecimal cost,
        Long serviceGroupId
) {
    // ********************** DTOs **********************
    public static ServiceDTO toDTO(ServiceOffering serviceOffering) {
        return new ServiceDTO(
                serviceOffering.cost(),
                serviceOffering.serviceGroupId()
        );
    }

}

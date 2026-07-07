package com.sashia.ecommerce.domain.category.dto;

import java.time.LocalDateTime;

public record CategoryDTO(
        Long id,
        String name,
        String url,
        String icon,
        Long parentId,
        Integer displayOrder,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}

package com.atrastones.shop.dto.create;

import java.math.BigDecimal;

public record ProductCreateDTO(String name,
                               BigDecimal price,
                               Long quantity,
                               Long categoryId,
                               String description,
                               Long serviceGroupId) {
}

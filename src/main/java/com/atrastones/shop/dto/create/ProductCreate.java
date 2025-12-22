package com.atrastones.shop.dto.create;

import java.math.BigDecimal;

public record ProductCreate(String name,
                            BigDecimal price,
                            Long quantity,
                            Long categoryId,
                            String description,
                            Long serviceGroupId) {
}

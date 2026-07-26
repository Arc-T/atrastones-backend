package com.sashia.ecommerce.promotion.type.discount.dto;

import com.sashia.ecommerce.promotion.type.discount.type.DiscountTypeCode;

import java.math.BigDecimal;

public record DiscountDTO(
        BigDecimal amount,
        DiscountTypeCode type
) {
}

package com.sashia.ecommerce.domain.discount;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountStrategy {

    BigDecimal calculate(BigDecimal price);

}
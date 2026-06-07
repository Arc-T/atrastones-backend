package com.atrastones.ecommerce.discount;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountStrategy {

    BigDecimal calculate(BigDecimal price);

}
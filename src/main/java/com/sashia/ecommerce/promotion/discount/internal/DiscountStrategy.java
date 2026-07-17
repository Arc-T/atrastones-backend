package com.sashia.ecommerce.promotion.discount.internal;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountStrategy {

    BigDecimal calculate(BigDecimal price);

}
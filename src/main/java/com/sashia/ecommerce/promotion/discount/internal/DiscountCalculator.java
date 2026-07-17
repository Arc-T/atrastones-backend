package com.sashia.ecommerce.promotion.discount.internal;

import java.math.BigDecimal;

public interface DiscountCalculator {

    DiscountStrategy noDiscount();

    DiscountStrategy fixedDiscount(BigDecimal amount);

    DiscountStrategy percentDiscount(BigDecimal percentage);

}

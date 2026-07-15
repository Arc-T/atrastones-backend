package com.sashia.ecommerce.discount.internal;

import java.math.BigDecimal;

public interface DiscountCalculator {

    DiscountStrategy noDiscount();

    DiscountStrategy fixedDiscount(BigDecimal amount);

    DiscountStrategy percentDiscount(BigDecimal percentage);

}

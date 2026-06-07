package com.atrastones.ecommerce.discount;

import java.math.BigDecimal;

public interface DiscountCalculator {

    DiscountStrategy noDiscount();

    DiscountStrategy fixedDiscount(BigDecimal amount);

    DiscountStrategy percentDiscount(BigDecimal percentage);

}

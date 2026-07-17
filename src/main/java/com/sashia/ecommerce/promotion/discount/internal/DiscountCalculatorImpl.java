package com.sashia.ecommerce.promotion.discount.internal;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DiscountCalculatorImpl implements DiscountCalculator {

    @Override
    public DiscountStrategy percentDiscount(BigDecimal percentage) {
        return price -> price.multiply(percentage).divide(BigDecimal.valueOf(100), RoundingMode.DOWN);
    }

    @Override
    public DiscountStrategy noDiscount() {
        return price -> price;
    }

    @Override
    public DiscountStrategy fixedDiscount(BigDecimal amount) {
        return price -> price.subtract(amount);
    }

}

package com.sashia.ecommerce.domain.discount;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountCalculatorImpl implements DiscountCalculator {

    @Override
    public DiscountStrategy percentDiscount(BigDecimal percentage) {
        return price -> price.multiply(percentage).divide(BigDecimal.valueOf(100));
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

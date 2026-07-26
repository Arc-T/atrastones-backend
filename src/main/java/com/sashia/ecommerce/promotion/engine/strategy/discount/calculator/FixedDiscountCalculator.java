package com.sashia.ecommerce.promotion.engine.strategy.discount.calculator;

import com.sashia.ecommerce.catalog.item.ItemDTO;
import com.sashia.ecommerce.promotion.type.discount.dto.DiscountDTO;
import com.sashia.ecommerce.promotion.type.discount.type.DiscountTypeCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixedDiscountCalculator implements DiscountCalculator {

    @Override
    public BigDecimal calculate(ItemDTO item, DiscountDTO discount) {
        return discount.amount();
    }

}
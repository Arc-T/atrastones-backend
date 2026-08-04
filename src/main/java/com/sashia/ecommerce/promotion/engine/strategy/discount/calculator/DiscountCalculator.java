package com.sashia.ecommerce.promotion.engine.strategy.discount.calculator;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.promotion.discount.dto.DiscountDTO;

import java.math.BigDecimal;

public interface DiscountCalculator {

    BigDecimal calculate(ItemDTO item, DiscountDTO discount);

}

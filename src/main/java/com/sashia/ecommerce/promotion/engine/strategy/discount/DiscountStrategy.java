package com.sashia.ecommerce.promotion.engine.strategy.discount;

import com.sashia.ecommerce.catalog.item.ItemDTO;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.effect.DiscountEffect;
import com.sashia.ecommerce.promotion.engine.effect.DiscountedItem;
import com.sashia.ecommerce.promotion.engine.strategy.PromotionStrategy;
import com.sashia.ecommerce.promotion.engine.strategy.discount.calculator.DiscountCalculator;
import com.sashia.ecommerce.promotion.engine.strategy.discount.calculator.FixedDiscountCalculator;
import com.sashia.ecommerce.promotion.engine.strategy.discount.calculator.PercentDiscountCalculator;
import com.sashia.ecommerce.promotion.type.TypeCode;
import com.sashia.ecommerce.promotion.discount.dto.DiscountDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Produces a {@link DiscountEffect} for a discount promotion.
 *
 * <p>The strategy calculates the discount amount for every applicable item
 * and aggregates the results into a single effect. No item prices are
 * modified here; the generated effect is applied later by the
 * {@code PromotionEffectApplier}.
 */
@Component
public class DiscountStrategy implements PromotionStrategy {

    private final FixedDiscountCalculator fixedDiscountCalculator;
    private final PercentDiscountCalculator percentDiscountCalculator;

    public DiscountStrategy(FixedDiscountCalculator fixedDiscountCalculator, PercentDiscountCalculator percentDiscountCalculator) {
        this.fixedDiscountCalculator = fixedDiscountCalculator;
        this.percentDiscountCalculator = percentDiscountCalculator;
    }

    @Override
    public TypeCode type() {
        return TypeCode.DISCOUNT;
    }

    @Override
    public void execute(PromotionContext context) {

        DiscountDTO discount = context.getPromotion().discount();

        DiscountCalculator calculator = switch (discount.type()) {
            case FIXED -> fixedDiscountCalculator;
            case PERCENT -> percentDiscountCalculator;
        };

        List<DiscountedItem> discountedItems = new ArrayList<>();

        for (ItemDTO item : context.getApplicableItems()) {

            BigDecimal discountAmount =
                    calculator.calculate(item, discount);

            discountedItems.add(
                    new DiscountedItem(
                            item.id(),
                            discountAmount
                    )
            );
        }

        context.addEffect(
                new DiscountEffect(
                        context.getPromotion().id(),
                        discountedItems
                )
        );
    }

}
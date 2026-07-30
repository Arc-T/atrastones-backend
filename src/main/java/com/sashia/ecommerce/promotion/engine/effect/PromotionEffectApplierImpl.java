package com.sashia.ecommerce.promotion.engine.effect;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.dto.PromotionContext;
import com.sashia.ecommerce.promotion.engine.dto.DiscountedItem;
import com.sashia.ecommerce.promotion.engine.dto.PromotionResult;
import com.sashia.ecommerce.promotion.engine.dto.AppliedPromotion;
import com.sashia.ecommerce.promotion.engine.dto.PricedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromotionEffectApplierImpl implements PromotionEffectApplier {

    private static final Logger log = LoggerFactory.getLogger(PromotionEffectApplierImpl.class);

    @Override
    public void apply(PromotionContext context, PromotionResult result) {

        log.debug("Applicable items: {}", context.getApplicableItems());

        for (PromotionEffect effect : context.getEffects()) {
            applyEffect(effect, context, result);
        }
    }

    private void applyEffect(PromotionEffect effect, PromotionContext context, PromotionResult result) {
        switch (effect) {
            case FreeShippingEffect shippingEffect -> IO.println(shippingEffect);
            case BuyXGetYEffect buyXGetYEffect -> IO.println(buyXGetYEffect);
            case DiscountEffect discountEffect -> applyDiscountEffect(discountEffect, context, result);
            default -> throw new IllegalArgumentException("Unsupported promotion effect: "
                    + effect.getClass().getSimpleName()
            );
        }
    }

    private void applyDiscountEffect(DiscountEffect effect, PromotionContext context, PromotionResult result) {

        PromotionDTO promotion = context.getPromotion();

        log.debug("Discounted items: {}", effect.discountedItems());

        for (DiscountedItem discountedItem : effect.discountedItems()) {

            PricedItem pricedItem = result.getPricedItem(discountedItem.itemId());

            BigDecimal priceBefore = pricedItem.getCurrentPrice();

            BigDecimal priceAfter = priceBefore.subtract(discountedItem.amount());

            pricedItem.addPromotion(
                    new AppliedPromotion(
                            promotion.id(),
                            promotion.name(),
                            promotion.type(),
                            discountedItem.amount(),
                            priceBefore,
                            priceAfter
                    )
            );
        }

    }

}
package com.sashia.ecommerce.promotion.engine;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.effect.DiscountEffect;
import com.sashia.ecommerce.promotion.engine.effect.DiscountedItem;
import com.sashia.ecommerce.promotion.engine.effect.PromotionEffect;
import com.sashia.ecommerce.promotion.engine.price.AppliedPromotion;
import com.sashia.ecommerce.promotion.engine.price.PricedItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromotionEffectApplierImpl implements PromotionEffectApplier {

    @Override
    public void apply(PromotionContext context, PromotionResult result) {

        for (PromotionEffect effect : context.getEffects()) {
            applyEffect(effect, context, result);
        }
    }

    private void applyEffect(PromotionEffect effect, PromotionContext context, PromotionResult result) {

        if (effect instanceof DiscountEffect discountEffect) {
            applyDiscountEffect(discountEffect, context, result);
            return;
        }

        throw new IllegalArgumentException("Unsupported promotion effect: "
                        + effect.getClass().getSimpleName()
        );
    }

    private void applyDiscountEffect(DiscountEffect effect, PromotionContext context, PromotionResult result) {

        PromotionDTO promotion = context.getPromotion();

        for (DiscountedItem discountedItem : effect.discountedItems()) {

            PricedItem pricedItem = result.getPricedItem(discountedItem.itemId());

            BigDecimal priceBefore = pricedItem.getCurrentPrice();

            BigDecimal priceAfter =
                    priceBefore.subtract(discountedItem.amount());

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
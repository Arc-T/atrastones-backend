package com.sashia.ecommerce.promotion.engine.effect;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.context.PromotionContext;
import com.sashia.ecommerce.promotion.engine.dto.AppliedPromotion;
import com.sashia.ecommerce.promotion.engine.dto.DiscountedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PromotionEffectApplierImpl implements PromotionEffectApplier {

    private static final Logger log = LoggerFactory.getLogger(PromotionEffectApplierImpl.class);

    @Override
    public void apply(PromotionContext context) {

        log.debug("Applicable items: {}", context.getApplicableItems());

        for (PromotionEffect effect : context.getEffects()) {
            applyEffect(effect, context);
        }
    }

    private void applyEffect(PromotionEffect effect, PromotionContext context) {
        switch (effect) {
            case FreeShippingEffect shippingEffect -> IO.println(shippingEffect);
            case BuyXGetYEffect buyXGetYEffect -> IO.println(buyXGetYEffect);
            case DiscountEffect discountEffect -> applyDiscountEffect(discountEffect, context);
            default -> throw new IllegalArgumentException("Unsupported promotion effect: "
                    + effect.getClass().getSimpleName()
            );
        }
    }

    private void applyDiscountEffect(DiscountEffect effect, PromotionContext context) {

        PromotionDTO promotion = context.getPromotion();

        log.debug("Discounted items: {}", effect.discountedItems());

        Map<Long, ItemDTO> items = context.getRequest()
                .items()
                .stream()
                .collect(Collectors.toUnmodifiableMap(ItemDTO::id, itemDTO -> itemDTO));
        for (DiscountedItem discountedItem : effect.discountedItems()) {

            ItemDTO itemDTO = items.get(discountedItem.itemId());

            BigDecimal priceAfter = itemDTO.basePrice().subtract(discountedItem.amount());

            BigDecimal benefitPrice = itemDTO.basePrice().subtract(priceAfter);

//            if (context.getRequest().order() != null)
//                result.addPromotionBenefit(promotion.name(), benefitPrice);

            itemDTO.promotions().add(
                    new AppliedPromotion(
                            promotion.id(),
                            promotion.name(),
                            promotion.type(),
                            discountedItem.amount(),
                            itemDTO.basePrice(),
                            priceAfter
                    )
            );
        }

    }

}
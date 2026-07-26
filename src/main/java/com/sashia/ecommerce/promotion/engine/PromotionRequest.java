package com.sashia.ecommerce.promotion.engine;

import com.sashia.ecommerce.catalog.item.ItemDTO;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record PromotionRequest(
        @Nullable Long userId,
        @Nullable String couponCode,
//        @Nullable ShippingMethod method
//        @Nullable PaymentType paymentType
        @NonNull List<ItemDTO> items
) {
}
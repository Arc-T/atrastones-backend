package com.sashia.ecommerce.promotion.engine.dto;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.ordering.order.dto.OrderDTO;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record PromotionRequest(
        @Nullable Long userId,
        @Nullable OrderDTO order,
        @NonNull List<ItemDTO> items) {
}
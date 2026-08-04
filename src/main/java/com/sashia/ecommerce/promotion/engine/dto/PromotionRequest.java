package com.sashia.ecommerce.promotion.engine.dto;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.ordering.order.dto.OrderCreateRequest;
import com.sashia.ecommerce.ordering.shipment.internal.ShipmentDTO;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record PromotionRequest(
        @Nullable Long userId,
        @Nullable OrderCreateRequest order,
        @Nullable List<ShipmentDTO> shipments,
        @NonNull List<ItemDTO> items) {

    public PromotionRequest(List<ItemDTO> items) {
        this(null, null, null, items);
    }

}
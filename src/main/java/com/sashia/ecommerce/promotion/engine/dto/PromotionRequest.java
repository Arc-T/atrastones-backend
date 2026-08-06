package com.sashia.ecommerce.promotion.engine.dto;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.ordering.order.dto.CheckoutRequest;
import com.sashia.ecommerce.ordering.shipment.internal.ShipmentDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record PromotionRequest(
        @Nullable Long userId,
        @Nullable CheckoutRequest order,
        @Nullable List<ShipmentDTO> shipments,
        @Nullable List<ItemDTO> items) {

    public static PromotionRequest ofCheckout(Long userId, CheckoutRequest order) {
        return new PromotionRequest(userId, order, null, null);
    }

    public static PromotionRequest ofItems(List<ItemDTO> items) {
        return new PromotionRequest(null, null, null, items);
    }

    public static PromotionRequest ofShipments(List<ShipmentDTO> shipments) {
        return new PromotionRequest(null, null, shipments, null);
    }

    public static PromotionRequest ofOrder(CheckoutRequest order) {
        return new PromotionRequest(null, order, null, null);
    }

}
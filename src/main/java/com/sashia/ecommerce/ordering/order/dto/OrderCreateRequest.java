package com.sashia.ecommerce.ordering.order.dto;

import com.sashia.ecommerce.billing.payment.dto.PaymentMethod;
import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.ordering.shipment.ShipmentMethod;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record OrderCreateRequest(
        @Nullable String coupon,
        @Nullable String description,
        @NonNull PaymentMethod paymentMethod,
        @NonNull ShipmentMethod shipmentMethod,
        @NonNull List<ItemDTO> items) {
}

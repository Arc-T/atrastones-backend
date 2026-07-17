package com.sashia.ecommerce.ordering.order;

import jakarta.persistence.Embeddable;

@Embeddable
public record DeliveryDetails(
        String deliveryAddress,
        String receiverName,
        String receiverPhone,
        String receiverEmail
) {
}

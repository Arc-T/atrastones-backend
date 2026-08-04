package com.sashia.ecommerce.ordering.shipment.internal;

import com.sashia.ecommerce.ordering.shipment.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}

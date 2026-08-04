package com.sashia.ecommerce.ordering.shipment.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShipmentService {

    Page<ShipmentDTO> readAll(Pageable pageable);

}

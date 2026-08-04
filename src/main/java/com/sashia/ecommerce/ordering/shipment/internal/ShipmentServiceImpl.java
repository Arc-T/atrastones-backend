package com.sashia.ecommerce.ordering.shipment.internal;

import com.sashia.ecommerce.promotion.engine.PromotionEngine;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShipmentServiceImpl implements ShipmentService {

    private final PromotionEngine promotionEngine;
    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(PromotionEngine promotionEngine, ShipmentRepository shipmentRepository) {
        this.promotionEngine = promotionEngine;
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Page<ShipmentDTO> readAll(Pageable pageable) {

        List<ShipmentDTO> shipments = shipmentRepository.findAll(pageable)
                .stream().map(ShipmentMapper::toDTO).toList();

//        promotionEngine.apply(new PromotionRequest(shipments));

        return new PageImpl<>(shipments, pageable, shipments.size());
    }

}
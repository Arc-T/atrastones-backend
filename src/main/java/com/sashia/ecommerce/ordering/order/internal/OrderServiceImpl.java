package com.sashia.ecommerce.ordering.order.internal;

import com.sashia.ecommerce.catalog.item.ItemService;
import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.ordering.order.OrderService;
import com.sashia.ecommerce.ordering.order.dto.CheckoutRequest;
import com.sashia.ecommerce.ordering.order.dto.OrderDTO;
import com.sashia.ecommerce.ordering.order.dto.OrderSearchDTO;
import com.sashia.ecommerce.promotion.engine.PromotionEngine;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import com.sashia.shared.exception.ResourceNotFoundException;
import com.sashia.shared.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final ItemService itemService;
    private final PromotionEngine promotionEngine;

    public OrderServiceImpl(ItemService itemService, PromotionEngine promotionEngine) {
        this.itemService = itemService;
        this.promotionEngine = promotionEngine;
    }

    @Override
    @Transactional
    public Long create(CheckoutRequest request) {
        //TODO: Validate the quantity of items
        //TODO: Validate the shipping method
        //TODO: Validate promotion if available
        //TODO: Validate total sum of the request with calculated
        //TODO: Idempotency for payment

        List<ItemDTO> items = new ArrayList<>(request.items().size());

        for (var item : request.items()) {
            items.add(itemService.get(item.id())
                    .orElseThrow(() -> new ResourceNotFoundException("product.not.found")));
        }

        Long userId = SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

//        promotionEngine.apply(PromotionRequest.ofCheckout(userId, request));

        return 0L;
    }

    @Override
    public Optional<OrderDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<OrderDTO> getAll(Pageable pageable, OrderSearchDTO search) {
//        return orderRepository.findAll(pageable, search).map(OrderDTO::toDTO);
        return null;
    }

    @Override
    public void update(Long id, OrderDTO order) {

    }

}

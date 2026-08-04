package com.sashia.ecommerce.ordering.order.internal;

import com.sashia.ecommerce.catalog.item.ItemService;
import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.catalog.product.ProductService;
import com.sashia.ecommerce.ordering.order.OrderRepository;
import com.sashia.ecommerce.ordering.order.OrderService;
import com.sashia.ecommerce.ordering.order.dto.OrderCreateRequest;
import com.sashia.ecommerce.ordering.order.dto.OrderDTO;
import com.sashia.ecommerce.ordering.order.dto.OrderSearchDTO;
import com.sashia.ecommerce.promotion.engine.PromotionEngine;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import com.sashia.ecommerce.promotion.engine.dto.PromotionResult;
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
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final PromotionEngine promotionEngine;

    public OrderServiceImpl(ItemService itemService, ProductService productService, OrderRepository orderRepository, PromotionEngine promotionEngine) {
        this.itemService = itemService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.promotionEngine = promotionEngine;
    }

    @Override
    public Long create(OrderCreateRequest request) {

        List<ItemDTO> items = new ArrayList<>(request.items().size());

        for (var item : request.items()) {
            items.add(itemService.get(item.id())
                    .orElseThrow(() -> new ResourceNotFoundException("product.not.found")));
        }

        Long userId = SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        PromotionResult promotionResults = promotionEngine.apply(
                new PromotionRequest(userId, request.coupon(), request.shipmentMethod(),
                        request.paymentMethod(), items)
        );

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

package com.sashia.ecommerce.ordering.order.internal;

import com.sashia.ecommerce.catalog.item.ItemService;
import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import com.sashia.ecommerce.catalog.item.dto.ItemDeliveryMethod;
import com.sashia.ecommerce.catalog.item.dto.ItemType;
import com.sashia.ecommerce.identity.user.UserRepository;
import com.sashia.ecommerce.ordering.order.*;
import com.sashia.ecommerce.ordering.order.dto.CheckoutRequest;
import com.sashia.ecommerce.ordering.order.dto.OrderDTO;
import com.sashia.ecommerce.ordering.order.dto.OrderSearchDTO;
import com.sashia.ecommerce.ordering.order.dto.OrderStatusType;
import com.sashia.ecommerce.ordering.shipment.ShipmentService;
import com.sashia.ecommerce.ordering.shipment.internal.ShipmentDTO;
import com.sashia.ecommerce.promotion.engine.PromotionEngine;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import com.sashia.shared.exception.BusinessRuleException;
import com.sashia.shared.exception.ResourceNotFoundException;
import com.sashia.shared.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final ItemService itemService;
    private final UserRepository userRepository;
    private final PromotionEngine promotionEngine;
    private final ShipmentService shipmentService;

    public OrderServiceImpl(ItemService itemService, UserRepository userRepository, PromotionEngine promotionEngine, ShipmentService shipmentService) {
        this.itemService = itemService;
        this.userRepository = userRepository;
        this.promotionEngine = promotionEngine;
        this.shipmentService = shipmentService;
    }

    @Override
    @Transactional
    public Long create(CheckoutRequest request) {

        Map<Long, ItemDTO> requestItems = request.items()
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        ItemDTO::id,
                        Function.identity()
                ));

        List<ItemDTO> items = new ArrayList<>();

        BigDecimal subtotal = BigDecimal.ZERO;

        for (var checkoutItem : request.items()) {

            ItemDTO item = itemService.get(checkoutItem.id())
                    .orElseThrow(() -> new ResourceNotFoundException("product.not.found"));

            if (item.quantity() < checkoutItem.quantity()) {
                throw new BusinessRuleException("checkout.product.quantity.exceed");
            }

            subtotal = subtotal.add(item.basePrice().multiply(BigDecimal.valueOf(checkoutItem.quantity())));

            items.add(item);
        }

        Long userId = SecurityUtils.getCurrentUserId()
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));

        promotionEngine.apply(PromotionRequest.ofCheckout(userId, request));

        BigDecimal itemDiscount = BigDecimal.ZERO;

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (var item : items) {

            if (!item.hasPromotion() && requestItems.get(item.id()).hasPromotion()) {

                throw new BusinessRuleException("checkout.promotions.not.available");

            } else if (item.hasPromotion() && !requestItems.get(item.id()).hasPromotion()) {

                throw new BusinessRuleException("checkout.promotions.not.applied");

            } else {

                totalPrice = item.discountedPrice() != null ?
                        item.discountedPrice().multiply(BigDecimal.valueOf(checkoutItem.quantity())) :
                        item.basePrice().multiply(BigDecimal.valueOf(checkoutItem.quantity()));
            }
        }

        ShipmentDTO shipment = shipmentService.read(request.delivery().shipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("shipping.method.invalid"));

        totalPrice = totalPrice.add(shipment.cost());

        if (!totalPrice.equals(request.sumTotal())) {
            throw new BusinessRuleException("checkout.price.not.matched");
        }

        Order order = new Order();
        order.setStatus(OrderStatusType.PENDING);
        order.setUserNote(request.description());
        order.setItemType(ItemType.PRODUCT);
        order.setUser(userRepository.getReferenceById(userId));

        order.setDelivery(
                new DeliveryDetails(
                        ItemDeliveryMethod.SHIPPING,
                        request.delivery().address(),
                        request.delivery().receiverName(),
                        request.delivery().receiverPhone(),
                        request.delivery().receiverEmail()
                )
        );

        order.setPricing(
                new PricingDetails(
                        CurrencyType.IRR,
                        subtotal,
                        shipment.cost(),
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        itemDiscount,
                        totalPrice)
        );

        for (var item : items) {

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setChargeType(OrderChargeType.ITEM_VARIANT);
            orderDetails.setChargeTypeId(item.id());
            orderDetails.setChargeTypeName(item.title());
            if (!item.promotions().isEmpty())
                orderDetails.setDiscountAmount(item.);

        }

        return order.getId();
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

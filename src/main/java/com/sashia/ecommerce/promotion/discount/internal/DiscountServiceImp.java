package com.sashia.ecommerce.promotion.discount.internal;

import com.sashia.ecommerce.catalog.item.product.dto.ProductDTO;
import com.sashia.ecommerce.catalog.item.product.dto.ProductPriceDTO;
import com.sashia.ecommerce.promotion.discount.Discount;
import com.sashia.ecommerce.promotion.discount.DiscountService;
import com.sashia.ecommerce.promotion.discount.dto.*;
import com.sashia.ecommerce.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DiscountServiceImp implements DiscountService {

    private final DiscountCalculator discountCalculator;
    private final DiscountRepository discountRepository;

    public DiscountServiceImp(DiscountCalculator discountCalculator, DiscountRepository discountRepository) {
        this.discountCalculator = discountCalculator;
        this.discountRepository = discountRepository;
    }

    @Override
    @Transactional
    public Long save(DiscountCreateDTO discount) {
//        if (getActiveDiscount().isPresent()) {
//            throw new BusinessRuleException("maximum.active.discounts");
//        }
//        DiscountContext discountContext = DiscountContext.from(discount);
//        if (!discountContext.applyToAll) {
//            if (discount.targets().isEmpty())
//                throw new InvalidRequestException("discount.selectionType.required");
//        }
//        Long savedDiscountId = discountRepository.save(discount);
//        if (!discountContext.applyToAll) {
//            discountRepository.saveIncludedTarget(savedDiscountId, discount.targets());
//        }
//        return savedDiscountId;
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    @Transactional
    public void update(Long id, DiscountEditDTO discountEdit) {
//        discountRepository.update(id, discountEdit);
    }

    @Override
    public DiscountDTO get(Long id) {
        return discountRepository.findById(id).map(DiscountDTO::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("discount.not.found"));
    }

    @Override
    public Page<DiscountDTO> getAll(Pageable pageable, DiscountSearchDTO search) {
//        return discountRepository.findAll(pageable, search).map(DiscountDTO::toDTO);
        return null;
    }

    //    @Cacheable(value = "activeDiscount", unless = "#result == null")
    public Optional<DiscountDTO> getActiveDiscount() {
//        return discountRepository.getActiveDiscount().map(DiscountDTO::toDTO);
        return null;
    }

    @Override
    public List<ProductPriceDTO> applyDiscountToProducts(DiscountDTO discount, List<ProductDTO> products) {
        DiscountContext context = DiscountContext.from(discount);

        return products.stream()
                .map(product -> calculateDiscountedPrice(product, context))
                .toList();
    }

    // ================================ HELPERS ================================

    private DiscountStrategy getStrategy(DiscountContext context) {
        return switch (context.discountType().code()) {
            case FIXED -> discountCalculator.fixedDiscount(context.discountAmount());
            case PERCENT -> discountCalculator.percentDiscount(context.discountAmount());
            default -> discountCalculator.noDiscount();
        };
    }

    private BigDecimal calculatePrice(DiscountContext context, BigDecimal basePrice) {
        DiscountStrategy strategy = getStrategy(context);
        return strategy.calculate(basePrice);
    }

    private ProductPriceDTO calculateDiscountedPrice(ProductDTO product, DiscountContext context) {
        BigDecimal basePrice = product.price().basePrice();

        if (!context.shouldApplyDiscount(product.id())) {
            return ProductPriceDTO.toDTO(basePrice);
        }

        BigDecimal finalPrice = calculatePrice(context, basePrice);
        return ProductPriceDTO.toDTO(basePrice, finalPrice);
    }

    // ================================ TYPES ================================

    private record DiscountContext(
            boolean applyToAll,
            Collection targetIds,
            BigDecimal discountAmount,
            DiscountTypeDTO discountType
    ) {

        static DiscountContext from(DiscountDTO discount) {
            boolean applyToAll = discount.selectionType() == Discount.SelectionType.ALL;
            Set<Long> targetIds = applyToAll
                    ? Collections.emptySet()
                    : discount.targets().stream()
                    .map(DiscountTargetDTO::id)
                    .collect(Collectors.toCollection(HashSet::new));

            return new DiscountContext(
                    applyToAll,
                    targetIds,
                    discount.amount(),
                    discount.type()
            );
        }

        static DiscountContext from(DiscountCreateDTO discount) {
            boolean applyToAll = discount.selectionType() == Discount.SelectionType.ALL;
            List<Long> targetIds = applyToAll
                    ? Collections.emptyList()
                    : new LinkedList<>(discount.targets());

            return new DiscountContext(
                    applyToAll,
                    targetIds,
                    discount.amount(),
                    null
            );
        }

        boolean shouldApplyDiscount(Long productId) {
            return applyToAll || targetIds.contains(productId);
        }

    }

}
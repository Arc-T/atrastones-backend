package com.sashia.ecommerce.domain.catalog.item.product;

import com.sashia.ecommerce.domain.catalog.item.product.common.ProductDTO;
import com.sashia.ecommerce.domain.discount.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private final DiscountService discountService;

    public PriceServiceImpl(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public List<ProductPriceDTO> applySellPrice(List<ProductDTO> products) {
        return discountService.getActiveDiscount()
                .map(discount -> discountService.applyDiscountToProducts(discount, products))
                .orElseGet(() -> createDefaultPrices(products));
    }

    private List<ProductPriceDTO> createDefaultPrices(List<ProductDTO> products) {
        return products.stream()
                .map(p -> ProductPriceDTO.toDTO(p.price().basePrice()))
                .toList();
    }

}
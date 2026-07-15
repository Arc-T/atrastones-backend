package com.sashia.ecommerce.catalog.item.product.internal;

import com.sashia.ecommerce.catalog.item.internal.ItemVariantPriceService;
import com.sashia.ecommerce.catalog.item.product.dto.ProductPriceDTO;
import com.sashia.ecommerce.catalog.item.product.dto.ProductDTO;
import com.sashia.ecommerce.discount.DiscountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemVariantPriceServiceImpl implements ItemVariantPriceService {

    private final DiscountService discountService;

    public ItemVariantPriceServiceImpl(DiscountService discountService) {
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
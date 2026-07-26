package com.sashia.ecommerce.catalog.item;

import com.sashia.ecommerce.catalog.item.pricing.ItemPricingService;
import com.sashia.ecommerce.catalog.item.product.dto.ProductDTO;
import com.sashia.ecommerce.catalog.item.product.dto.ProductPriceDTO;
import com.sashia.ecommerce.promotion.discount.DiscountService;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemPricingService {

    private final DiscountService discountService;

    public ItemServiceImpl(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public ProductPriceDTO calculatePrice(ProductDTO product) {
        List<PromotionDTO> discounts = discountService.getActiveDiscounts();

    }

}

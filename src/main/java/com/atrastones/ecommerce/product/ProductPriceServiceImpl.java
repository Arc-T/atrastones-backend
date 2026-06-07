package com.atrastones.ecommerce.product;

import com.atrastones.ecommerce.discount.DiscountService;
import com.atrastones.ecommerce.product.common.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

    private final DiscountService discountService;

    public ProductPriceServiceImpl(DiscountService discountService) {
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
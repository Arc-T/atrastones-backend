package com.sashia.ecommerce.domain.catalog.item.product;

import com.sashia.ecommerce.domain.catalog.item.product.common.ProductDTO;

import java.util.List;

public interface PriceService {

    List<ProductPriceDTO> applySellPrice(List<ProductDTO> products);

}

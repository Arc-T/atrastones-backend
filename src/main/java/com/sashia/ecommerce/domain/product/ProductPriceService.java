package com.sashia.ecommerce.domain.product;

import com.sashia.ecommerce.domain.product.common.ProductDTO;

import java.util.List;

public interface ProductPriceService {

    List<ProductPriceDTO> applySellPrice(List<ProductDTO> products);

}

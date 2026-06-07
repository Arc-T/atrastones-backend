package com.atrastones.ecommerce.product;

import com.atrastones.ecommerce.product.common.ProductDTO;

import java.util.List;

public interface ProductPriceService {

    List<ProductPriceDTO> applySellPrice(List<ProductDTO> products);

}

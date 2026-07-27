package com.sashia.ecommerce.catalog.item.internal;

import com.sashia.ecommerce.catalog.product.dto.ProductDTO;
import com.sashia.ecommerce.catalog.product.dto.ProductPriceDTO;

import java.util.List;

public interface ItemVariantPriceService {

    List<ProductPriceDTO> applySellPrice(List<ProductDTO> products);

}

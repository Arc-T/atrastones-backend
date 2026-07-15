package com.sashia.ecommerce.catalog.item.internal;

import com.sashia.ecommerce.catalog.item.product.dto.ProductDTO;
import com.sashia.ecommerce.catalog.item.product.dto.ProductPriceDTO;

import java.util.List;

public interface ItemVariantPriceService {

    List<ProductPriceDTO> applySellPrice(List<ProductDTO> products);

}

package com.sashia.ecommerce.catalog.product;

import com.sashia.ecommerce.catalog.item.internal.ProductSearchRequest;
import com.sashia.ecommerce.catalog.product.dto.ProductBriefInfoProjection;
import com.sashia.ecommerce.catalog.product.dto.ProductCreateRequest;
import com.sashia.ecommerce.catalog.product.dto.ProductDTO;
import com.sashia.ecommerce.catalog.product.dto.ProductUpdateDTO;
import com.sashia.ecommerce.promotion.engine.dto.PromotionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Long create(ProductCreateRequest product);

    ProductDTO get(Long id);

    Page<PromotionResult> getAll(Pageable pageable, ProductSearchRequest search);

    Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchRequest search);

    void edit(Long id, ProductUpdateDTO product);

    void delete(Long id);

}

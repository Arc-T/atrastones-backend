package com.sashia.ecommerce.catalog.item.product;

import com.sashia.ecommerce.catalog.item.internal.ProductSearchRequest;
import com.sashia.ecommerce.catalog.item.product.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Long create(ProductCreateRequest product);

    ProductDTO get(Long id);

    Page<ProductSummary> getAll(Pageable pageable, ProductSearchRequest search);

    Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchRequest search);

    void edit(Long id, ProductUpdateDTO product);

    void delete(Long id);

}

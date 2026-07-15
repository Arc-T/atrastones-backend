package com.sashia.ecommerce.catalog.item.product;

import com.sashia.ecommerce.catalog.item.product.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Long create(ProductCreateRequest product);

    ProductDTO get(Long id);

    Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO search);

    Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchDTO search);

    void edit(Long id, ProductUpdateDTO product);

    void delete(Long id);

}

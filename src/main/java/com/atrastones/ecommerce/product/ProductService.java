package com.atrastones.ecommerce.product;

import com.atrastones.ecommerce.product.common.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    // ============================= CREATE =============================** //

    Long save(ProductCreateDTO product);

    // ============================= GET =============================** //

    ProductDTO get(Long id);

    Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO search);

    Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchDTO search);

    // ============================= UPDATE =============================** //

    void edit(Long id, ProductUpdateDTO product);

    // ============================= DELETE =============================** //

    void delete(Long id);

    // ============================= OPERATIONS =============================

    boolean exists(Long id);

}

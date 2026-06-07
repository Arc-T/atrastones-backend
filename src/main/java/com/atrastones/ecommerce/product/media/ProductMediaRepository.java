package com.atrastones.ecommerce.product.media;

import com.atrastones.ecommerce.product.ProductMediaDTO;

import java.util.List;
import java.util.Optional;

public interface ProductMediaRepository {

    // ======================================= CREATE =======================================

    List<Long> createBatch(List<ProductMediaDTO> productMediaDTO);

    // ======================================= UPDATE =======================================

    void update(Long id, ProductMediaDTO productMediaDTO);

    // ======================================= DELETE =======================================

    boolean delete(Long id);

    // ======================================= SELECT =======================================

    Optional<ProductMedia> get(Long id);

}

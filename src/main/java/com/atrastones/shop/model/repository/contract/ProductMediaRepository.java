package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.model.entity.ProductMedia;

import java.util.List;
import java.util.Optional;

public interface ProductMediaRepository {

    // ======================================= CREATE =======================================

    List<Long> createBatch(List<ProductMediaDTO> productMedia);

    // ======================================= UPDATE =======================================

    void update(Long id, ProductMediaDTO productMedia);

    // ======================================= DELETE =======================================

    boolean delete(Long id);

    // ======================================= SELECT =======================================

    Optional<ProductMedia> get(Long id);

}

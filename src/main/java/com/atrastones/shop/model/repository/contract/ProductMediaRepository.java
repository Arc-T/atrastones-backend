package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.model.entity.ProductMedia;

import java.util.List;
import java.util.Optional;

public interface ProductMediaRepository {

    // ======================================= CREATE =======================================

    List<Long> createBatch(List<com.atrastones.shop.dto.ProductMediaDTO> productMediaDTO);

    // ======================================= UPDATE =======================================

    void update(Long id, com.atrastones.shop.dto.ProductMediaDTO productMediaDTO);

    // ======================================= DELETE =======================================

    boolean delete(Long id);

    // ======================================= SELECT =======================================

    Optional<ProductMedia> get(Long id);

}

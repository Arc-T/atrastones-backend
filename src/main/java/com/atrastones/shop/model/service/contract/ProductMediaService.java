package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.ProductMediaDTO;

import java.util.List;

public interface ProductMediaService {

    /* ******************************** CRUD ******************************** */

    List<ProductMediaDTO> getAll();

    Long create(ProductMediaDTO productMediaDTO);

}

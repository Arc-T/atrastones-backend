package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.api.create.ProductMediaCreate;
import com.atrastones.shop.dto.ProductMediaDTO;

import java.util.List;

public interface ProductMediaService {

    /* ******************************** CRUD ******************************** */

    List<Long> save(Long productId);

    void delete(Long id);

    List<ProductMediaDTO> getAllDraft();

    void createDraft(ProductMediaCreate productMediaCreate);

}

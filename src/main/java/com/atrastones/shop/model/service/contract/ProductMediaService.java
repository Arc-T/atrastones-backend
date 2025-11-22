package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.ProductMediaDTO;

import java.util.List;

public interface ProductMediaService {

    /* ******************************** CRUD ******************************** */

    void create(List<ProductMediaDTO> createProductMedia);

    List<ProductMediaDTO> getAllDraft();

}

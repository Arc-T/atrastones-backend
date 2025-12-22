package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.MediaDTO;
import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.dto.create.ProductMediaCreate;

import java.util.List;

public interface ProductMediaService {

    /* ******************************** CRUD ******************************** */

    List<Long> save(Long productId);

    void deleteDraft(String fileName);

    void deleteProductMedia(Long productId, String fileName);

    List<MediaDTO> getAllDraft();

    void createDraft(ProductMediaCreate productMediaCreate);

}

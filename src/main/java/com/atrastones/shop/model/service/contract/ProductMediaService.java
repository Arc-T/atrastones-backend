package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.MediaDTO;
import com.atrastones.shop.dto.create.ProductMediaCreate;
import com.atrastones.shop.dto.update.ProductMediaUpdate;

import java.util.List;

public interface ProductMediaService {

    /* ******************************** CRUD ******************************** */

    List<Long> save(Long productId);

    void updateProductMedia(Long productId, ProductMediaUpdate productMediaUpdate);

    void deleteDraft(String fileName);

    void deleteProductMedia(Long productId, String fileName);

    List<MediaDTO> getAllDraft();

    List<MediaDTO> getProductMedia(Long productId);

    void createDraft(ProductMediaCreate productMediaCreate);

}

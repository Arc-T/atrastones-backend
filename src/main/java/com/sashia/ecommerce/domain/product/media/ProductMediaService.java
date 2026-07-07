package com.sashia.ecommerce.domain.product.media;

import com.sashia.ecommerce.domain.product.MediaDTO;

import java.util.List;

public interface ProductMediaService {

    /* ******************************** CRUD ******************************** */

    List<Long> save(Long productId);

    void update(Long productId);

    void saveDraft(ProductMediaCreateDTO productMediaCreateDTO);

    void saveDraft(Long productId, ProductMediaCreateDTO productMediaCreateDTO);

    void delete(Long id);

    void deleteDraft(String fileName);

    void deleteDraft(Long productId, String fileName);

    List<MediaDTO> getAllDraft();

    List<MediaDTO> getProductDraft(Long productId);

}

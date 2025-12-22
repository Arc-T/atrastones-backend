package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.MediaDTO;
import com.atrastones.shop.dto.create.ProductMediaCreate;
import com.atrastones.shop.exception.ServiceLogicException;
import com.atrastones.shop.model.repository.contract.ProductMediaRepository;
import com.atrastones.shop.model.service.contract.ProductMediaService;
import com.atrastones.shop.utils.MediaUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductMediaServiceImp implements ProductMediaService {

    private final ProductMediaRepository productMediaRepository;

    public ProductMediaServiceImp(ProductMediaRepository productMediaRepository) {
        this.productMediaRepository = productMediaRepository;
    }

    @Override
    @Transactional
    public List<Long> save(Long productId) {
        return productMediaRepository.createBatch(MediaUtils.moveAllDraftsToProduct(productId));
    }

    @Override
    public void deleteDraft(String fileName) {
        MediaUtils.deleteDraftFile(fileName);
    }

    @Override
    public void deleteProductMedia(Long productId, String url) {
        if (productMediaRepository.deleteByProductIdAndUrl(productId, url))
            MediaUtils.deleteProductMedia(productId, url);
        throw new ServiceLogicException("INVALID.PRODUCT.AND.URL"); //TODO: message
    }

    @Override
    public void createDraft(ProductMediaCreate create) {
        List<MediaDTO> draftMedia = MediaUtils.draft(create.media());
        if (draftMedia.isEmpty() || draftMedia.size() != create.media().length)
            throw new ServiceLogicException("ALL.MEDIA.DID.NOT.SAVED"); //TODO: message
    }

    @Override
    public List<MediaDTO> getAllDraft() {
        return MediaUtils.listDrafts();
    }

}
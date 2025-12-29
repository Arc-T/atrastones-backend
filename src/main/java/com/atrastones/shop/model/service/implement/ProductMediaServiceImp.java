package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.MediaDTO;
import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.dto.create.ProductMediaCreate;
import com.atrastones.shop.exception.ServiceLogicException;
import com.atrastones.shop.model.repository.contract.ProductMediaRepository;
import com.atrastones.shop.model.repository.contract.ProductRepository;
import com.atrastones.shop.model.service.contract.ProductMediaService;
import com.atrastones.shop.utils.MediaUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductMediaServiceImp implements ProductMediaService {

    private final ProductRepository productRepository;
    private final ProductMediaRepository productMediaRepository;

    public ProductMediaServiceImp(ProductRepository productRepository, ProductMediaRepository productMediaRepository) {
        this.productRepository = productRepository;
        this.productMediaRepository = productMediaRepository;
    }

    @Override
    @Transactional
    public List<Long> save(Long productId) {
        return productMediaRepository.createBatch(MediaUtils.moveAllDraftsToProduct(productId));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductMediaDTO productMedia = productMediaRepository.get(id)
                .map(ProductMediaDTO::toDTO)
                .orElseThrow(() -> new ServiceLogicException("INVALID.PRODUCT_MEDIA.ID"));

        if (productMediaRepository.delete(productMedia.id()))
            MediaUtils.deleteProductMedia(productMedia.productId(), productMedia.url());
        else
            throw new ServiceLogicException("SYSTEM_ERROR.PRODUCT_MEDIA.DELETE");
    }

    @Override
    public void deleteDraft(String fileName) {
        MediaUtils.deleteDraft(fileName);
    }

    @Override
    public void deleteDraft(Long productId, String filename) {
        MediaUtils.deleteDraftProductMedia(productId, filename);
    }

    @Override
    public void saveDraft(ProductMediaCreate productMedia) {
        List<MediaDTO> draftMedia = MediaUtils.draft(productMedia.media());
        if (draftMedia.isEmpty() || draftMedia.size() != productMedia.media().length)
            throw new ServiceLogicException("ALL.MEDIA.DID.NOT.SAVED"); //TODO: message
    }

    @Override
    public void saveDraft(Long productId, ProductMediaCreate request) {
        if (productRepository.exists(productId)) {
            List<MediaDTO> draftMedia = MediaUtils.draft(productId, request.media());
            if (draftMedia.isEmpty() || draftMedia.size() != request.media().length)
                throw new ServiceLogicException("ALL.MEDIA.DID.NOT.SAVED"); //TODO: message
        } else
            throw new ServiceLogicException("PRODUCT.NOT.FOUND");
    }

    @Override
    public List<MediaDTO> getAllDraft() {
        return MediaUtils.listDraft();
    }

    @Override
    public List<MediaDTO> getProductDraft(Long productId) {
        return MediaUtils.listProductDraft(productId);
    }

}
package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.api.create.ProductMediaCreate;
import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.exception.ServiceLogicException;
import com.atrastones.shop.model.entity.ProductMedia;
import com.atrastones.shop.model.repository.contract.ProductMediaRepository;
import com.atrastones.shop.model.service.contract.ProductMediaService;
import com.atrastones.shop.utils.MediaUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMediaServiceImp implements ProductMediaService {

    private final ProductMediaRepository productMediaRepository;

    public ProductMediaServiceImp(ProductMediaRepository productMediaRepository) {
        this.productMediaRepository = productMediaRepository;
    }

    @Override
    public List<Long> save(Long productId) {
        return productMediaRepository.createBatch(MediaUtils.moveAllDraftsToProduct(productId));
    }

    @Override
    public void delete(Long productId) {
        MediaUtils.deleteFile(productId,
                productMediaRepository.get(productId)
                        .map(ProductMedia::getUrl)
                        .orElseThrow(() -> new ServiceLogicException("PRODUCT_MEDIA.NOT.FOUND")) //TODO: message
                );
    }

    @Override
    public void createDraft(ProductMediaCreate create) {
        List<ProductMediaDTO> draftMedia = MediaUtils.draft(create.media());
        if (draftMedia.isEmpty() || draftMedia.size() != create.media().length)
            throw new ServiceLogicException("ALL.MEDIA.DID.NOT.SAVED"); //TODO: message
    }

    @Override
    public List<ProductMediaDTO> getAllDraft() {
        return MediaUtils.listDrafts();
    }

}
package com.sashia.ecommerce.domain.catalog.media;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.common.exception.ResourceNotFoundException;
import com.sashia.ecommerce.domain.catalog.item.product.ProductRepository;
import com.sashia.ecommerce.domain.catalog.media.dto.MediaCreateRequest;
import com.sashia.ecommerce.domain.catalog.media.dto.MediaResponse;
import com.sashia.ecommerce.domain.catalog.media.utils.MediaUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final ProductRepository productRepository;

    public MediaServiceImpl(ProductRepository productRepository, MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<Long> create(Long productId) {
        return mediaRepository.createBatch(
                MediaUtils.moveAllDraftsToProduct(productId, false)
        );
    }

    @Override
    @Transactional
    public void update(Long productId) {
        mediaRepository.createBatch(
                MediaUtils.moveAllDraftsToProduct(productId, true)
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Media productMedia = mediaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("media.not.found"));

//        if (mediaRepository.delete(productMedia.id()))
//            MediaUtils.deleteProductMedia(productMedia.productId(), productMedia.url());
//        else
//            throw new InvalidResourceException("SYSTEM_ERROR.PRODUCT_MEDIA.DELETE");
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
    public void createDraft(MediaCreateRequest productMedia) {
        List<MediaResponse> draftMedia = MediaUtils.draft(productMedia.media());
        if (draftMedia.isEmpty() || draftMedia.size() != productMedia.media().length)
            throw new InvalidResourceException("ALL.MEDIA.DID.NOT.SAVED"); //TODO: message
    }

    @Override
    public void createDraft(Long productId, MediaCreateRequest request) {
        if (productRepository.exists(productId)) {
            List<MediaResponse> draftMedia = MediaUtils.draft(productId, request.media());
            if (draftMedia.isEmpty() || draftMedia.size() != request.media().length)
                throw new InvalidResourceException("ALL.MEDIA.DID.NOT.SAVED"); //TODO: message
        } else
            throw new InvalidResourceException("PRODUCT.NOT.FOUND");
    }

    @Override
    public List<MediaResponse> readAllDraft() {
        return MediaUtils.listDraft();
    }

    @Override
    public List<MediaResponse> readDraft(Long productId) {
        return MediaUtils.listProductDraft(productId);
    }

}
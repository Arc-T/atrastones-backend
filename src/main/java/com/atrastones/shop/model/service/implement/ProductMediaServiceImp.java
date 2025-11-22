package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.ProductMediaDTO;
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
    public void create(List<ProductMediaDTO> createProductMedia) {
        List<Long> insertedMedia = productMediaRepository.createBatch(
                MediaUtils.upload(createdProductId, product.getMedia()));
        if (insertedMedia.isEmpty() || insertedMedia.size() != product.getMedia().size())
            throw new RuntimeException("Media Inserted Problem");
    }

    @Override
    public List<ProductMediaDTO> getAllDraft() {
        return List.of();
    }

}

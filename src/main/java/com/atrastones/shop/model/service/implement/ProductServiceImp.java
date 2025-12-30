package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.*;
import com.atrastones.shop.dto.create.ProductCreateDTO;
import com.atrastones.shop.dto.projection.ProductProjection;
import com.atrastones.shop.dto.search.ProductSearchDTO;
import com.atrastones.shop.dto.update.ProductUpdateDTO;
import com.atrastones.shop.model.repository.contract.ProductRepository;
import com.atrastones.shop.model.service.contract.ProductMediaService;
import com.atrastones.shop.model.service.contract.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMediaService productMediaService;

    public ProductServiceImp(ProductRepository productRepository, ProductMediaService productMediaService) {
        this.productRepository = productRepository;
        this.productMediaService = productMediaService;
    }

    @Override
    public Optional<ProductDTO> get(Long id) {
        return productRepository.get(id).map(ProductDTO::toDTO);
    }

    @Override
    public void remove(Long productId) {
    }

    @Override
    @Transactional
    public Long save(ProductCreateDTO product) {
        long productId = productRepository.create(product);
        productMediaService.save(productId);
        return productId;
    }

    @Override
    @Transactional
    public void edit(Long id, ProductUpdateDTO product) {
        productRepository.update(id, product);
        productMediaService.update(id);
    }

    @Override
    public Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO filter) {
        return productRepository.getAll(pageable, filter);
    }

    @Override
    public List<ProductDTO> getProductsInfoByIds(List<Long> productIds) {
        return List.of();
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId, String sortType) {
        return List.of();
    }

    @Override
    public List<ProductDTO> searchProductsByAttribute(List<AttributeValueDTO> attributes) {
        return List.of();
    }

    @Override
    public List<TagDTO> getProductTags(Long productId) {
        return List.of();
    }

    @Override
    public List<ProductStatDTO> getProductStats(Long productId) {
        return List.of();
    }

    @Override
    public List<ProductMediaDTO> getProductMedia(Long productId) {
        return List.of();
    }

    @Override
    public List<ProductReviewDTO> getProductReviews(Long productId) {
        return List.of();
    }

    @Override
    public List<ProductAttributeValueDTO> getProductAttributes(Long productId) {
        return List.of();
    }

    // *************************************** OPERATIONS ***************************************

    @Override
    public boolean exists(Long id) {
        return productRepository.exists(id);
    }

}

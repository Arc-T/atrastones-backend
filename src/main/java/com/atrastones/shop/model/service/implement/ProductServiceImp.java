package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.api.create.ProductCreate;
import com.atrastones.shop.api.filter.ProductFilter;
import com.atrastones.shop.dto.*;
import com.atrastones.shop.model.repository.contract.ProductRepository;
import com.atrastones.shop.model.service.contract.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDTO> get(Long productId) {
        return Optional.empty();
    }

    @Override
    public void remove(Long productId) {
    }

    @Override
    public Long save(ProductCreate product) {
        return productRepository.create(product);
    }

    @Override
    public void edit(Long id, ProductDTO product) {

    }

    @Override
    public List<ProductDTO> getAll(ProductFilter filter) {
        return List.of();
    }

    @Override
    public Page<ProductDTO> getAllPaginated(Pageable pageable, ProductFilter filter) {
        return productRepository.getAllPaginated(pageable, filter).map(ProductDTO::toDTO);
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
        return false;
    }

}

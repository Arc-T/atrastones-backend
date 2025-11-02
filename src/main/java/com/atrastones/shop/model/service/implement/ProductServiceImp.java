package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.*;
import com.atrastones.shop.model.entity.Product;
import com.atrastones.shop.model.repository.contract.ProductRepository;
import com.atrastones.shop.model.service.contract.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Long save(ProductDTO product) {
        return 0L;
    }

    @Override
    public void edit(Long id, ProductDTO product) {

    }

    @Override
    public List<ProductDTO> getAllListed() {
        return List.of();
    }

    @Override
    public Page<ProductDTO> getAllPaginated(Pageable pageable) {
        return productRepository.getAllPaginated(pageable).map(this::toDTO);
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

    private ProductDTO toDTO(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategory().getId())
                .shopId(product.getShop().getId())
                .quantity(product.getQuantity())
                .price(product.getPrice())
//                .discountId(product.getDiscount().getId())
//                .discountAmount(product.getDiscount().getAmount())
                .status(product.getStatus())
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

    }

}

package com.sashia.ecommerce.catalog.item.product.internal;

import com.sashia.ecommerce.catalog.category.Category;
import com.sashia.ecommerce.catalog.category.CategoryRepository;
import com.sashia.ecommerce.catalog.item.product.Product;
import com.sashia.ecommerce.catalog.item.product.ProductRepository;
import com.sashia.ecommerce.catalog.item.product.ProductService;
import com.sashia.ecommerce.catalog.item.product.dto.*;
import com.sashia.ecommerce.catalog.item.internal.ItemVariantPriceService;
import com.sashia.ecommerce.promotion.discount.DiscountService;
import com.sashia.ecommerce.media.MediaService;
import com.sashia.ecommerce.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImp implements ProductService {

    private final MediaService mediaService;
    private final ItemVariantPriceService itemVariantPriceService;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository, MediaService mediaService, DiscountService discountService, CategoryRepository categoryRepository, ItemVariantPriceService itemVariantPriceService) {
        this.productRepository = productRepository;
        this.mediaService = mediaService;
        this.categoryRepository = categoryRepository;
        this.itemVariantPriceService = itemVariantPriceService;
    }

    @Override
    @Transactional
    public Long create(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category.not.found"));

        Product product = ProductMapper.toEntity(request, category);
        productRepository.save(product);
//        mediaService.create(productId);
        return product.getId();
    }

    @Override
    public ProductDTO get(Long id) {
//        return productRepository.get(id).map(ProductDTO::toDTO)
//                .orElseThrow(() -> new InvalidResourceException("product.not.found"));
        return null;
    }

    @Override
    public Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO filter) {
//        return productRepository.findAll(pageable, filter);
        return null;
    }

    @Override
    public Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchDTO search) {
//        Page<ProductDTO> productPage = productRepository.getAllBriefInfo(pageable, search)
//                .map(ProductDTO::toDTO);

//        List<ProductPriceDTO> productPrices = productPriceService.applySellPrice(productPage.getContent());
//
//        return PageableExecutionUtils.getPage(
//                ProductBriefInfoProjection.toListDTO(productPage.getContent(), productPrices),
//                pageable,
//                productPage::getTotalElements
//        );
        return null;
    }

    @Override
    @Transactional
    public void edit(Long id, ProductUpdateDTO product) {
//        productRepository.update(id, product);
//        mediaService.update(id);
    }

    @Override
    public void delete(Long productId) {
    }

}

package com.sashia.ecommerce.catalog.item.product.internal;

import com.sashia.ecommerce.catalog.category.Category;
import com.sashia.ecommerce.catalog.category.CategoryRepository;
import com.sashia.ecommerce.catalog.item.ItemRepository;
import com.sashia.ecommerce.catalog.item.internal.ItemSpecification;
import com.sashia.ecommerce.catalog.item.internal.ItemVariantPriceService;
import com.sashia.ecommerce.catalog.item.internal.ProductSearchRequest;
import com.sashia.ecommerce.catalog.item.product.Product;
import com.sashia.ecommerce.catalog.item.product.ProductRepository;
import com.sashia.ecommerce.catalog.item.product.ProductService;
import com.sashia.ecommerce.catalog.item.product.dto.*;
import com.sashia.ecommerce.promotion.discount.DiscountService;
import com.sashia.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImp implements ProductService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImp(ProductRepository productRepository, DiscountService discountService,
                             CategoryRepository categoryRepository, ItemVariantPriceService itemVariantPriceService,
                             ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
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
    public Page<ProductSummary> getAll(Pageable pageable, ProductSearchRequest request) {
        return itemRepository.findAll(ItemSpecification.bySearch(request), pageable)
                .map(ProductMapper::toSummary);
    }

    @Override
    public Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchRequest request) {
        Page<ProductSummary> productPage = itemRepository.findAll(ItemSpecification.bySearch(request), pageable)
                .map((item) -> DiscountService.applyDiscountToProducts(item))
                .map(ProductMapper::toSummary);

//        List<ProductPriceDTO> productPrices = productPriceService.applySellPrice(productPage.getContent());
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

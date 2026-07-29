package com.sashia.ecommerce.catalog.product.internal;

import com.sashia.ecommerce.catalog.category.Category;
import com.sashia.ecommerce.catalog.category.CategoryRepository;
import com.sashia.ecommerce.catalog.item.ItemDTO;
import com.sashia.ecommerce.catalog.item.ItemMapper;
import com.sashia.ecommerce.catalog.item.ItemRepository;
import com.sashia.ecommerce.catalog.item.internal.ItemSpecification;
import com.sashia.ecommerce.catalog.item.internal.ProductSearchRequest;
import com.sashia.ecommerce.catalog.product.Product;
import com.sashia.ecommerce.catalog.product.ProductRepository;
import com.sashia.ecommerce.catalog.product.ProductService;
import com.sashia.ecommerce.catalog.product.dto.*;
import com.sashia.ecommerce.promotion.engine.PromotionEngine;
import com.sashia.ecommerce.promotion.engine.PromotionRequest;
import com.sashia.ecommerce.promotion.engine.PromotionResult;
import com.sashia.shared.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImp implements ProductService {

    private final ItemRepository itemRepository;
    private final PromotionEngine promotionEngine;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImp(ProductRepository productRepository, CategoryRepository categoryRepository,
                             ItemRepository itemRepository, PromotionEngine promotionEngine) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.promotionEngine = promotionEngine;
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
    public Page<PromotionResult> getAll(Pageable pageable, ProductSearchRequest request) {

        Page<ItemDTO> items = itemRepository.findAll(ItemSpecification.bySearch(request), pageable)
                .map(ItemMapper::toDTO);

        PromotionResult result = promotionEngine.apply(new PromotionRequest(items.stream().toList()));

        return new PageImpl<>(List.of(result), pageable, items.getTotalElements());
    }

    @Override
    public Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchRequest request) {
        Page<ProductSummary> productPage = itemRepository.findAll(ItemSpecification.bySearch(request), pageable)
//                .map((item) -> DiscountService.applyDiscountToProducts(item))
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

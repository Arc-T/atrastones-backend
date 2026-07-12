package com.sashia.ecommerce.domain.catalog.item.product;

import com.sashia.ecommerce.domain.catalog.item.product.common.*;
import com.sashia.ecommerce.domain.catalog.media.MediaService;
import com.sashia.ecommerce.domain.discount.DiscountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final MediaService mediaService;
    private final PriceService priceService;

    public ProductServiceImp(ProductRepository productRepository, MediaService mediaService, DiscountService discountService, PriceService priceService) {
        this.productRepository = productRepository;
        this.mediaService = mediaService;
        this.priceService = priceService;
    }

    @Override
    @Transactional
    public Long save(ProductCreateDTO product) {
        long productId = productRepository.create(product);
        mediaService.create(productId);
        return productId;
    }

    @Override
    public ProductDTO get(Long id) {
//        return productRepository.get(id).map(ProductDTO::toDTO)
//                .orElseThrow(() -> new InvalidResourceException("product.not.found"));
        return null;
    }

    @Override
    public Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO filter) {
        return productRepository.getAll(pageable, filter);
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
        productRepository.update(id, product);
        mediaService.update(id);
    }

    @Override
    public void delete(Long productId) {
    }

    // *************************************** OPERATIONS ***************************************

    @Override
    public boolean exists(Long id) {
        return productRepository.exists(id);
    }

}

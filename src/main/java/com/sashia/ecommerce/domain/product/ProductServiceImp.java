package com.sashia.ecommerce.domain.product;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.domain.discount.DiscountService;
import com.sashia.ecommerce.domain.product.common.*;
import com.sashia.ecommerce.domain.product.media.ProductMediaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMediaService productMediaService;
    private final ProductPriceService productPriceService;

    public ProductServiceImp(ProductRepository productRepository, ProductMediaService productMediaService, DiscountService discountService, ProductPriceService productPriceService) {
        this.productRepository = productRepository;
        this.productMediaService = productMediaService;
        this.productPriceService = productPriceService;
    }

    @Override
    @Transactional
    public Long save(ProductCreateDTO product) {
        long productId = productRepository.create(product);
        productMediaService.save(productId);
        return productId;
    }

    @Override
    public ProductDTO get(Long id) {
        return productRepository.get(id).map(ProductDTO::toDTO)
                .orElseThrow(() -> new InvalidResourceException("product.not.found"));
    }

    @Override
    public Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO filter) {
        return productRepository.getAll(pageable, filter);
    }

    @Override
    public Page<ProductBriefInfoProjection> getAllBriefInfo(Pageable pageable, ProductSearchDTO search) {
        Page<ProductDTO> productPage = productRepository.getAllBriefInfo(pageable, search)
                .map(ProductDTO::toDTO);

        List<ProductPriceDTO> productPrices = productPriceService.applySellPrice(productPage.getContent());

        return PageableExecutionUtils.getPage(
                ProductBriefInfoProjection.toListDTO(productPage.getContent(), productPrices),
                pageable,
                productPage::getTotalElements
        );
    }

    @Override
    @Transactional
    public void edit(Long id, ProductUpdateDTO product) {
        productRepository.update(id, product);
        productMediaService.update(id);
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

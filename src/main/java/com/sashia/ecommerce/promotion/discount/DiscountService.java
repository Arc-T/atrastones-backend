package com.sashia.ecommerce.promotion.discount;

import com.sashia.ecommerce.catalog.item.product.dto.ProductPriceDTO;
import com.sashia.ecommerce.catalog.item.product.dto.ProductDTO;
import com.sashia.ecommerce.promotion.discount.dto.DiscountCreateDTO;
import com.sashia.ecommerce.promotion.discount.dto.DiscountDTO;
import com.sashia.ecommerce.promotion.discount.dto.DiscountEditDTO;
import com.sashia.ecommerce.promotion.discount.dto.DiscountSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DiscountService {

    /* ============================= CREATE ============================= */

    Long save(DiscountCreateDTO discount);

    /* ============================= READ ============================= */

    DiscountDTO get(Long id);

    Page<DiscountDTO> getAll(Pageable pageable, DiscountSearchDTO search);

    Optional<DiscountDTO> getActiveDiscount();

    /* ============================= UPDATE ============================= */

    void update(Long id, DiscountEditDTO discountEdit);

    /* ============================= DELETE ============================= */

    void delete(Long id);

    /* ============================= OPERATIONS ============================= */

    List<ProductPriceDTO> applyDiscountToProducts(DiscountDTO discount, List<ProductDTO> product);

}
package com.sashia.ecommerce.domain.discount;

import com.sashia.ecommerce.domain.catalog.item.product.ProductPriceDTO;
import com.sashia.ecommerce.domain.catalog.item.product.common.ProductDTO;
import com.sashia.ecommerce.domain.discount.common.DiscountCreateDTO;
import com.sashia.ecommerce.domain.discount.common.DiscountDTO;
import com.sashia.ecommerce.domain.discount.common.DiscountEditDTO;
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
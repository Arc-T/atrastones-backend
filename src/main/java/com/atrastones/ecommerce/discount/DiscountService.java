package com.atrastones.ecommerce.discount;

import com.atrastones.ecommerce.discount.common.DiscountCreateDTO;
import com.atrastones.ecommerce.discount.common.DiscountDTO;
import com.atrastones.ecommerce.discount.common.DiscountEditDTO;
import com.atrastones.ecommerce.product.ProductPriceDTO;
import com.atrastones.ecommerce.product.common.ProductDTO;
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
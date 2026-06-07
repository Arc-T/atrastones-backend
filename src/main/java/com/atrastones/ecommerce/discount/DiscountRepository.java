package com.atrastones.ecommerce.discount;

import com.atrastones.ecommerce.discount.common.DiscountCreateDTO;
import com.atrastones.ecommerce.discount.common.DiscountEditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository {

    /* ============================= CREATE ============================= */

    Long save(DiscountCreateDTO discount);

    Long[] saveIncludedTarget(Long discountId, List<Long> targetIds);

    /* ============================= GET ============================= */

    Optional<Discount> get(Long id);

    Optional<Discount> getActiveDiscount();

    List<Discount> getApplicableDiscountsForProduct(Long productId);

    Page<Discount> getAll(Pageable pageable, DiscountSearchDTO search);

    /* ============================= UPDATE ============================= */

    void update(Long id, DiscountEditDTO discount);

    /* ============================= DELETE ============================= */

    void delete(Long id);

    /* ============================= OPERATIONS ============================= */

    Long count();

}

package com.sashia.ecommerce.promotion.discount.internal;

import com.sashia.ecommerce.promotion.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}

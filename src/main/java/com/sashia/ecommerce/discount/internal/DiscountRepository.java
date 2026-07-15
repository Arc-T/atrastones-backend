package com.sashia.ecommerce.discount.internal;

import com.sashia.ecommerce.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}

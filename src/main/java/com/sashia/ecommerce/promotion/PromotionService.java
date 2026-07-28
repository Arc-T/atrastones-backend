package com.sashia.ecommerce.promotion;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {

    Page<PromotionDTO> readAll(Pageable pageable);

    List<PromotionDTO> getActivePromotions();

}

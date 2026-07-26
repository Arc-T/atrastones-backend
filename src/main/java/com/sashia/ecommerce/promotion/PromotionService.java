package com.sashia.ecommerce.promotion;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;

import java.util.List;

public interface PromotionService {

    List<PromotionDTO> getActivePromotions();

}

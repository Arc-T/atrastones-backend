package com.sashia.ecommerce.promotion.engine.resolver;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;

import java.util.List;

public interface PromotionResolver {

    List<PromotionDTO> resolve(PromotionRequest request);

}
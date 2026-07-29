package com.sashia.ecommerce.promotion.engine.resolver;

import com.sashia.ecommerce.promotion.PromotionService;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.engine.dto.PromotionRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionResolverImpl implements PromotionResolver {

    private final PromotionService promotionService;

    public PromotionResolverImpl(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Override
    public List<PromotionDTO> resolve(PromotionRequest request) {
        return promotionService.getActivePromotions();
    }

}

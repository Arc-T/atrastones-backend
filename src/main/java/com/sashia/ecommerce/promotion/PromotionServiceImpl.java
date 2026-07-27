package com.sashia.ecommerce.promotion;

import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<PromotionDTO> getActivePromotions() {
//        return promotionRepository.findAllActivePromotions().stream().map(PromotionMapper::toDTO);
        return null;
    }


}

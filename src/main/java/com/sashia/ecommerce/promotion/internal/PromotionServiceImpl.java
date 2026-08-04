package com.sashia.ecommerce.promotion.internal;

import com.sashia.ecommerce.promotion.Promotion;
import com.sashia.ecommerce.promotion.PromotionMapper;
import com.sashia.ecommerce.promotion.PromotionRepository;
import com.sashia.ecommerce.promotion.PromotionService;
import com.sashia.ecommerce.promotion.discount.Discount;
import com.sashia.ecommerce.promotion.discount.DiscountRepository;
import com.sashia.ecommerce.promotion.dto.PromotionCreateRequest;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.dto.PromotionUpdateRequest;
import com.sashia.ecommerce.promotion.type.TypeCode;
import com.sashia.shared.exception.BusinessRuleException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PromotionServiceImpl implements PromotionService {

    private final DiscountRepository discountRepository;
    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(DiscountRepository discountRepository, PromotionRepository promotionRepository) {
        this.discountRepository = discountRepository;
        this.promotionRepository = promotionRepository;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "promotions")
    public Long create(PromotionCreateRequest request) {
        return 0L;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "promotions")
    public void update(Long id, PromotionUpdateRequest request) {

    }

    @Override
    public Page<PromotionDTO> readAll(Pageable pageable) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = "promotions", unless = "#result == null")
    public List<PromotionDTO> getActivePromotions() {

        List<Promotion> promotions = promotionRepository.findAllActivePromotions();

        List<PromotionDTO> promotionDTOs = new ArrayList<>(promotions.size());

        for (var promotion : promotions) {

            if (promotion.getType().getCode() == TypeCode.DISCOUNT) {

                Discount discount = discountRepository.findByPromotionId(promotion.getId()).
                        orElseThrow(() -> new BusinessRuleException("Promotion has no discount")); //TODO: message

                promotionDTOs.add(PromotionMapper.toDTO(promotion, discount));
            }
        }

        return promotionDTOs;
    }

}

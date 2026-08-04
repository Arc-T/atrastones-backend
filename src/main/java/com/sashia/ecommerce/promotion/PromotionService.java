package com.sashia.ecommerce.promotion;

import com.sashia.ecommerce.promotion.dto.PromotionCreateRequest;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import com.sashia.ecommerce.promotion.dto.PromotionUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {

    Long create(PromotionCreateRequest request);

    void update(Long id, PromotionUpdateRequest request);

    Page<PromotionDTO> readAll(Pageable pageable);

    List<PromotionDTO> getActivePromotions();

}

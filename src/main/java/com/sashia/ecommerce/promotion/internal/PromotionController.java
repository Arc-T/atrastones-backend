package com.sashia.ecommerce.promotion.internal;

import com.sashia.ecommerce.promotion.PromotionService;
import com.sashia.ecommerce.promotion.dto.PromotionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')") //TODO: assign authorize
    ResponseEntity<List<PromotionDTO>> readAll() {
        return ResponseEntity.ok(promotionService.getActivePromotions());
    }

}

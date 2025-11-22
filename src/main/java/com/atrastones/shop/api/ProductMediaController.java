package com.atrastones.shop.api;

import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.model.service.contract.ProductMediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/product-Media")
public class ProductMediaController {

    private final ProductMediaService productMediaService;


    public ProductMediaController(ProductMediaService productMediaService) {
        this.productMediaService = productMediaService;
    }

    @GetMapping("/draft")
    public ResponseEntity<List<ProductMediaDTO>> readAllDraft() {
        return ResponseEntity.ok().body(productMediaService.getAllDraft());
    }

}

package com.atrastones.shop.controller;

import com.atrastones.shop.dto.MediaDTO;
import com.atrastones.shop.dto.create.ProductMediaCreateDTO;
import com.atrastones.shop.model.service.contract.ProductMediaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product-media")
public class ProductMediaController {

    private final ProductMediaService productMediaService;

    public ProductMediaController(ProductMediaService productMediaService) {
        this.productMediaService = productMediaService;
    }

    // ================================ GET ================================

    @GetMapping("/draft")
    public ResponseEntity<List<MediaDTO>> readAllDraft() {
        return ResponseEntity.ok().body(productMediaService.getAllDraft());
    }

    @GetMapping("/draft/{productId}")
    public ResponseEntity<List<MediaDTO>> readAllDraft(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productMediaService.getProductDraft(productId));
    }

    // ================================ POST ================================

    @PostMapping(path = "/draft", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDraft(ProductMediaCreateDTO ProductMediaCreateDTO) {
        productMediaService.saveDraft(ProductMediaCreateDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/draft/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDraft(@PathVariable Long productId, ProductMediaCreateDTO ProductMediaCreateDTO) {
        productMediaService.saveDraft(productId, ProductMediaCreateDTO);
        return ResponseEntity.noContent().build();
    }

    // ================================ DELETE ================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productMediaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/draft/{name}")
    public ResponseEntity<?> deleteDraft(@PathVariable String name) {
        productMediaService.deleteDraft(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/draft/{productId}/{name}")
    public ResponseEntity<?> deleteDraft(@PathVariable Long productId, @PathVariable String name) {
        productMediaService.deleteDraft(productId, name);
        return ResponseEntity.noContent().build();
    }

}

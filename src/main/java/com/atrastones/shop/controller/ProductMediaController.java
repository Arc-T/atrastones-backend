package com.atrastones.shop.controller;

import com.atrastones.shop.dto.MediaDTO;
import com.atrastones.shop.dto.create.ProductMediaCreate;
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

    @GetMapping("/draft")
    public ResponseEntity<List<MediaDTO>> readAllDraft() {
        return ResponseEntity.ok().body(productMediaService.getAllDraft());
    }

    @PostMapping(path = "/draft", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createDraft(ProductMediaCreate ProductMediaCreate) {
        productMediaService.createDraft(ProductMediaCreate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{productId}/{name}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId, @PathVariable String name) {
        productMediaService.deleteProductMedia(productId, name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/draft/{name}")
    public ResponseEntity<Void> deleteDraft(@PathVariable String name) {
        productMediaService.deleteDraft(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id) {
//        productMediaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

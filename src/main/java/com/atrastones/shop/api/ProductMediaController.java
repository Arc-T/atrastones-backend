package com.atrastones.shop.api;

import com.atrastones.shop.api.create.ProductMediaCreate;
import com.atrastones.shop.dto.ProductMediaDTO;
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
    public ResponseEntity<List<ProductMediaDTO>> readAllDraft() {
        return ResponseEntity.ok().body(productMediaService.getAllDraft());
    }

    @PostMapping(path = "/draft", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createDraft(ProductMediaCreate ProductMediaCreate) {
        productMediaService.createDraft(ProductMediaCreate);
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

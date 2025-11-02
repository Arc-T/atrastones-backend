package com.atrastones.shop.api;

import com.atrastones.shop.dto.ProductDTO;
import com.atrastones.shop.model.service.contract.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> read(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.get(id));
    }

    @GetMapping(params = {"categoryId", "attributeIds"})
    public ResponseEntity<Page<ProductDTO>> readAll(Pageable pageable,
                                                    @RequestParam(required = false) Long categoryId,
                                                    @RequestParam(required = false) List<Long> attributeIds) {
        return ResponseEntity.ok(productService.getAllPaginated(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO product) {
        return ResponseEntity.created(URI.create("/products/" + productService.save(product)))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ProductDTO product) {
        productService.edit(id, product);
        return ResponseEntity.ok().build();
    }

}
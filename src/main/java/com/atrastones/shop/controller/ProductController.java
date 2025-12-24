package com.atrastones.shop.controller;

import com.atrastones.shop.dto.create.ProductCreate;
import com.atrastones.shop.dto.search.ProductSearch;
import com.atrastones.shop.dto.ProductDTO;
import com.atrastones.shop.model.service.contract.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductDTO>> read(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.get(id));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> readAllPaginated(Pageable pageable, ProductSearch filter) {
        return ResponseEntity.ok(productService.getAllPaginated(pageable, filter));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(ProductCreate product) {
        return ResponseEntity.created(URI.create("/products/" + productService.save(product)))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ProductDTO product) {
        productService.edit(id, product);
        return ResponseEntity.ok().build();
    }

}
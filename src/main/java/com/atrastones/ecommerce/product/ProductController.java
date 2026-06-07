package com.atrastones.ecommerce.product;

import com.atrastones.ecommerce.product.common.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/products")
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    ResponseEntity<Page<ProductProjection>> readAll(Pageable pageable, ProductSearchDTO search) {
        return ResponseEntity.ok(productService.getAll(pageable, search));
    }

    @GetMapping("/brief")
    ResponseEntity<Page<ProductBriefInfoProjection>> readAllBrief(Pageable pageable, ProductSearchDTO search) {
        return ResponseEntity.ok(productService.getAllBriefInfo(pageable, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_PRODUCT')")
    ResponseEntity<ProductDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    ResponseEntity<ProductDTO> create(ProductCreateDTO product) {
        return ResponseEntity.created(URI.create("/products/" + productService.save(product)))
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO product) {
        productService.edit(id, product);
        return ResponseEntity.noContent().build();
    }

}
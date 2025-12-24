package com.atrastones.shop.controller;

import com.atrastones.shop.dto.search.CategorySearch;
import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.model.service.contract.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoryDTO>> read(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> readAll(Pageable pageable, CategorySearch search) {
        return ResponseEntity.ok(categoryService.getAll(pageable, search));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO category) {
        return ResponseEntity.created(URI.create("/categories/" + categoryService.save(category)))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO category) {
        categoryService.edit(id, category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
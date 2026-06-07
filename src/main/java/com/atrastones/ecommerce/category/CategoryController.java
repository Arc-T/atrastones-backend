package com.atrastones.ecommerce.category;

import com.atrastones.ecommerce.category.common.CategoryCreateDTO;
import com.atrastones.ecommerce.category.common.CategoryDTO;
import com.atrastones.ecommerce.category.common.CategorySearchDTO;
import com.atrastones.ecommerce.category.common.CategoryUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')")
    public ResponseEntity<Page<CategoryDTO>> readAll(Pageable pageable, CategorySearchDTO search) {
        return ResponseEntity.ok(categoryService.readAll(pageable, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_CATEGORY')")
    public ResponseEntity<CategoryDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.read(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryCreateDTO category) {
        return ResponseEntity.created(URI.create("/categories/" + categoryService.create(category)))
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO category) {
        categoryService.update(id, category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
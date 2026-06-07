package com.atrastones.ecommerce.category;

import com.atrastones.ecommerce.category.common.CategoryCreateDTO;
import com.atrastones.ecommerce.category.common.CategorySearchDTO;
import com.atrastones.ecommerce.category.common.CategoryUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepository {

    // =============================== INSERT ===============================

    Long save(CategoryCreateDTO category);

    // =============================== SELECT ===============================

    Optional<Category> find(Long id);

    Page<Category> findAll(Pageable pageable, CategorySearchDTO search);

    // =============================== UPDATE ===============================

    void update(Long id, CategoryUpdateDTO category);

    // =============================== DELETE ===============================

    boolean delete(Long id);

    // =============================== OPERATIONS ===============================

    Long count();

    boolean exists(Long id);

}
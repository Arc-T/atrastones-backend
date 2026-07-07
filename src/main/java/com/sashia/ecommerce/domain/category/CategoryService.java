package com.sashia.ecommerce.domain.category;

import com.sashia.ecommerce.domain.category.dto.CategoryCreateDTO;
import com.sashia.ecommerce.domain.category.dto.CategoryDTO;
import com.sashia.ecommerce.domain.category.dto.CategorySearchDTO;
import com.sashia.ecommerce.domain.category.dto.CategoryUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    /* ============================= CREATE ============================= */

    Long create(CategoryCreateDTO category);

    /* ============================= READ ============================= */

    CategoryDTO read(Long id);

    Page<CategoryDTO> readAll(Pageable pageable, CategorySearchDTO search);

    /* ============================= UPDATE ============================= */

    void update(Long id, CategoryUpdateDTO category);

    /* ============================= DELETE ============================= */

    void delete(Long id);

    /* ============================= OPERATIONS ============================= */

    boolean exists(Long id);

}

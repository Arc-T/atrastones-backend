package com.atrastones.ecommerce.category;

import com.atrastones.ecommerce.category.common.CategoryCreateDTO;
import com.atrastones.ecommerce.category.common.CategoryDTO;
import com.atrastones.ecommerce.category.common.CategorySearchDTO;
import com.atrastones.ecommerce.category.common.CategoryUpdateDTO;
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

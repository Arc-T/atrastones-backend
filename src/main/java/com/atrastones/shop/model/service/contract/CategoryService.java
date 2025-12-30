package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.search.CategorySearchDTO;
import com.atrastones.shop.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {

    /* ******************************** CRUD ******************************** */

    Optional<CategoryDTO> get(Long id);

    Page<CategoryDTO> getAll(Pageable pageable, CategorySearchDTO search);

    void remove(Long id);

    Long save(CategoryDTO category);

    void edit(Long id, CategoryDTO category);

    /* ******************************** RELATIONS ******************************** */

    CategoryDTO index(); //Index page

}

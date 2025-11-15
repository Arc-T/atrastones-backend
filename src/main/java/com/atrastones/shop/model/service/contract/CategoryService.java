package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.api.search.CategorySearch;
import com.atrastones.shop.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    /* ******************************** CRUD ******************************** */

    Optional<CategoryDTO> get(Long id);

    List<CategoryDTO> getAll(CategorySearch filter);

    Page<CategoryDTO> getAllPageable(Pageable pageable, CategorySearch filter);

    CategoryDTO getAttributesAndValues(Long id);

    List<CategoryDTO> getAllParentsWithChildren();

    void remove(Long id);

    Long save(CategoryDTO category);

    void edit(Long id, CategoryDTO category);

    /* ******************************** RELATIONS ******************************** */

    CategoryDTO index(); //Index page

}

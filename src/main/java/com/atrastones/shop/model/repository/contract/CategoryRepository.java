package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.search.CategorySearch;
import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepository {

    // -------------------------------------- CREATE --------------------------------------

    Long create(CategoryDTO category);

    // -------------------------------------- UPDATE --------------------------------------

    void update(Long id, CategoryDTO category);

    // -------------------------------------- DELETE --------------------------------------

    boolean delete(Long id);

    // -------------------------------------- SELECT --------------------------------------

    Optional<Category> get(Long id);


    Page<Category> getAll(Pageable pageable, CategorySearch search);

    // -------------------------------------- OPERATIONS --------------------------------------

    long count();

    boolean exists(Long id);

}
package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.dto.search.CategorySearch;
import com.atrastones.shop.model.repository.contract.CategoryRepository;
import com.atrastones.shop.model.service.contract.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<CategoryDTO> get(Long id) {
        return categoryRepository.get(id).map(CategoryDTO::toDTO);
    }

    @Override
    public Page<CategoryDTO> getAll(Pageable pageable, CategorySearch filter) {
        return categoryRepository.getAll(pageable, filter)
                .map(CategoryDTO::toFullDTO);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        categoryRepository.delete(id);
    }

    @Override
    @Transactional
    public Long save(CategoryDTO category) {
        return categoryRepository.create(category);
    }

    @Override
    @Transactional
    public void edit(Long id, CategoryDTO category) {
        categoryRepository.update(id, category);
    }

    @Override
    public CategoryDTO index() {
        return null;
    }

}

package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.search.CategorySearch;
import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.model.repository.contract.CategoryRepository;
import com.atrastones.shop.model.service.contract.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return categoryRepository.get(id).map(CategoryDTO::toEntity);
    }

    @Override
    public List<CategoryDTO> getAll(CategorySearch filter) {
        return categoryRepository.getAll(filter)
                .stream()
                .map(CategoryDTO::toEntity)
                .toList();
    }

    @Override
    public Page<CategoryDTO> getAllPageable(Pageable pageable, CategorySearch filter) {
        return categoryRepository.getAllPaginated(pageable, filter)
                .map(CategoryDTO::toFullDTO);
    }

    @Override
    public CategoryDTO getAttributesAndValues(Long id) {
        return null;
    }

    @Override
    public List<CategoryDTO> getAllParentsWithChildren() {
        return List.of();
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

package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.CategoryDTO;
import com.atrastones.shop.mapper.contract.CategoryMapper;
import com.atrastones.shop.model.repository.contract.CategoryRepository;
import com.atrastones.shop.model.service.contract.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImp(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Optional<CategoryDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.getAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    @Override
    public Page<CategoryDTO> getAllPageable(Pageable pageable) {
        return categoryRepository.getAllPaginated(pageable)
                .map(categoryMapper::toDTO);
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
    public void remove(Long id) {
        categoryRepository.delete(id);
    }

    @Override
    public Long save(CategoryDTO category) {
        return categoryRepository.create(category);
    }

    @Override
    public void edit(Long id, CategoryDTO category) {
        categoryRepository.update(id, category);
    }

    @Override
    public CategoryDTO index() {
        return null;
    }

}

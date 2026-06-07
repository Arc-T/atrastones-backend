package com.atrastones.ecommerce.category;

import com.atrastones.ecommerce.category.common.CategoryCreateDTO;
import com.atrastones.ecommerce.category.common.CategoryDTO;
import com.atrastones.ecommerce.category.common.CategorySearchDTO;
import com.atrastones.ecommerce.category.common.CategoryUpdateDTO;
import com.atrastones.infrastructure.error.InvalidResourceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Long create(CategoryCreateDTO category) {
        if (category.parentId() != null) {
            Category parentCategory = categoryRepository.find(category.parentId())
                    .orElseThrow(() -> new InvalidResourceException("category.parentId.not.found"));
            if (parentCategory.parentId() != null)
                throw new InvalidResourceException("category.parentId.invalid");
        }
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDTO read(Long id) {
        return categoryRepository.find(id).map(CategoryDTO::toDTO)
                .orElseThrow(() -> new InvalidResourceException("category.not.found"));
    }

    @Override
    public Page<CategoryDTO> readAll(Pageable pageable, CategorySearchDTO filter) {
        return categoryRepository.findAll(pageable, filter).map(CategoryDTO::toDTO);
    }

    @Override
    @Transactional
    public void update(Long id, CategoryUpdateDTO category) {
        if (!categoryRepository.exists(id))
            throw new InvalidResourceException("category.not.found");

        if (category.parentId() != null) {
            Category parentCategory = categoryRepository.find(category.parentId())
                    .orElseThrow(() -> new InvalidResourceException("category.parentId.not.found"));
            if (parentCategory.parentId() != null)
                throw new InvalidResourceException("category.parentId.invalid");
        }
        categoryRepository.update(id, category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.exists(id))
            throw new InvalidResourceException("category.not.found");

        categoryRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        return categoryRepository.exists(id);
    }

}

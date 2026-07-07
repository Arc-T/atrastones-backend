package com.sashia.ecommerce.domain.category;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.domain.category.dto.CategoryCreateDTO;
import com.sashia.ecommerce.domain.category.dto.CategoryDTO;
import com.sashia.ecommerce.domain.category.dto.CategorySearchDTO;
import com.sashia.ecommerce.domain.category.dto.CategoryUpdateDTO;
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
            Category parentCategory = categoryRepository.findById(category.parentId())
                    .orElseThrow(() -> new InvalidResourceException("category.parentId.not.found"));
            if (parentCategory.getParentId() != null)
                throw new InvalidResourceException("category.parentId.invalid");
        }
//        return categoryRepository.save(category);
        return null;
    }

    @Override
    public CategoryDTO read(Long id) {
        return categoryRepository.findById(id).map(CategoryMapper::toDTO)
                .orElseThrow(() -> new InvalidResourceException("category.not.found"));
    }

    @Override
    public Page<CategoryDTO> readAll(Pageable pageable, CategorySearchDTO filter) {
        return categoryRepository.findAll(pageable).map(CategoryMapper::toDTO);
    }

    @Override
    @Transactional
    public void update(Long id, CategoryUpdateDTO category) {
//        if (!categoryRepository.exists(id))
//            throw new InvalidResourceException("category.not.found");
//
//        if (category.parentId() != null) {
//            Category parentCategory = categoryRepository.find(category.parentId())
//                    .orElseThrow(() -> new InvalidResourceException("category.parentId.not.found"));
//            if (parentCategory.parentId() != null)
//                throw new InvalidResourceException("category.parentId.invalid");
//        }
//        categoryRepository.update(id, category);

    }

    @Override
    @Transactional
    public void delete(Long id) {
//        if (!categoryRepository.exists(id))
//            throw new InvalidResourceException("category.not.found");
//
//        categoryRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        return true;
    }

}

package com.atrastones.ecommerce.attribute;

import com.atrastones.ecommerce.attribute.common.AttributeCreateDTO;
import com.atrastones.ecommerce.attribute.common.AttributeDTO;
import com.atrastones.ecommerce.attribute.common.AttributeSearchDTO;
import com.atrastones.ecommerce.attribute.common.AttributeUpdateDTO;
import com.atrastones.ecommerce.category.CategoryService;
import com.atrastones.infrastructure.error.InvalidResourceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class AttributeServiceImp implements AttributeService {

    private final CategoryService categoryService;
    private final AttributeRepository attributeRepository;

    public AttributeServiceImp(CategoryService categoryService, AttributeRepository attributeRepository) {
        this.categoryService = categoryService;
        this.attributeRepository = attributeRepository;
    }

    @Override
    @Transactional
    public Long create(AttributeCreateDTO attribute) {
        if (!categoryService.exists(attribute.categoryId()))
            throw new InvalidResourceException("category.not.found");

        return attributeRepository.save(attribute);
    }

    @Override
    public AttributeDTO read(Long id) {
        return attributeRepository.findById(id).map(AttributeDTO::toDTO)
                .orElseThrow(() -> new InvalidResourceException("attribute.not.found"));
    }

    @Override
    public Page<AttributeDTO> readAll(Pageable pageable, AttributeSearchDTO search) {
        return attributeRepository.findAll(search, pageable).map(AttributeDTO::toDTO);
    }

    @Override
    @Transactional
    public void update(Long id, AttributeUpdateDTO attribute) {
        if (!categoryService.exists(id))
            throw new InvalidResourceException("attribute.not.found");

        if (!categoryService.exists(attribute.categoryId()))
            throw new InvalidResourceException("attribute.category.not.found");

        attributeRepository.update(id, attribute);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoryService.exists(id))
            throw new InvalidResourceException("category.not.found");
        attributeRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

}
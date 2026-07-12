package com.sashia.ecommerce.domain.catalog.attribute;

import com.sashia.ecommerce.common.exception.ResourceNotFoundException;
import com.sashia.ecommerce.domain.catalog.attribute.dto.AttributeCreateRequest;
import com.sashia.ecommerce.domain.catalog.attribute.dto.AttributeResponse;
import com.sashia.ecommerce.domain.catalog.attribute.dto.AttributeSearchRequest;
import com.sashia.ecommerce.domain.catalog.attribute.dto.AttributeUpdateRequest;
import com.sashia.ecommerce.domain.catalog.category.Category;
import com.sashia.ecommerce.domain.catalog.category.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
class AttributeServiceImpl implements AttributeService {

    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;

    public AttributeServiceImpl(CategoryRepository categoryRepository, AttributeRepository attributeRepository) {
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
    }

    @Override
    @Transactional
    public Long create(AttributeCreateRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category.not.found"));

        Attribute attribute = AttributeMapper.toEntity(request, category);

        attribute.setCategory(category);

        if (request.isFilterable() && !CollectionUtils.isEmpty(request.values())) {

            for (var value : request.values())
                attribute.addAttributeValue(AttributeValueMapper.toEntity(value));
        }

        return attributeRepository.save(attribute).getId();
    }

    @Override
    public Optional<AttributeResponse> read(Long id) {
        return attributeRepository.findByIdWithFullDetails(id).map(AttributeMapper::toDTO);
    }

    @Override
    public Page<AttributeResponse> readAll(Pageable pageable, AttributeSearchRequest search) {
        return attributeRepository.findAll(AttributeSpecification.hasName(search.name()), pageable).map(AttributeMapper::toDTO);
    }

    @Override
    @Transactional
    public void update(Long id, AttributeUpdateRequest request) {
        Attribute attribute = attributeRepository.findByIdWithFullDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("attribute.not.found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category.not.found"));

        attribute.setCategory(category);

        if (request.isFilterable() && !CollectionUtils.isEmpty(request.values())) {
            attribute.clearAttributeValues();

            for (var value : request.values())
                attribute.addAttributeValue(AttributeValueMapper.toEntity(value));
        }

        AttributeMapper.update(attribute, request);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!attributeRepository.existsById(id))
            throw new ResourceNotFoundException("attribute.not.found"); //TODO: doesn't need to throw exception. just handle manually
        attributeRepository.deleteById(id);
    }

}
package com.sashia.ecommerce.domain.attribute;

import com.sashia.ecommerce.common.exception.InvalidResourceException;
import com.sashia.ecommerce.domain.attribute.dto.AttributeCreateRequest;
import com.sashia.ecommerce.domain.attribute.dto.AttributeResponse;
import com.sashia.ecommerce.domain.attribute.dto.AttributeSearchRequest;
import com.sashia.ecommerce.domain.attribute.dto.AttributeUpdateRequest;
import com.sashia.ecommerce.domain.attribute.value.AttributeValueMapper;
import com.sashia.ecommerce.domain.category.Category;
import com.sashia.ecommerce.domain.category.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
class AttributeServiceImp implements AttributeService {

    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;

    public AttributeServiceImp(CategoryRepository categoryRepository, AttributeRepository attributeRepository) {
        this.categoryRepository = categoryRepository;
        this.attributeRepository = attributeRepository;
    }

    @Override
    @Transactional
    public Long create(AttributeCreateRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new InvalidResourceException("category.not.found"));

        Attribute attribute = AttributeMapper.toEntity(request, category);

        if (request.isFilterable() && !CollectionUtils.isEmpty(request.values())) {
            request
                    .values()
                    .stream()
                    .map(value -> AttributeValueMapper.toEntity(value, attribute))
                    .forEach(attribute.getAttributeValues()::add);
        }

        return attributeRepository.save(attribute).getId();
    }

    @Override
    public Optional<AttributeResponse> read(Long id) {
        return attributeRepository.findByIdWithFullDetails(id).map(AttributeMapper::toDTO);
    }

    @Override
    public Page<AttributeResponse> readAll(Pageable pageable, AttributeSearchRequest search) {
        return attributeRepository.findAll(AttributeSpecs.hasName(search.name()), pageable).map(AttributeMapper::toDTO);
    }

    @Override
    @Transactional
    public void update(Long id, AttributeUpdateRequest request) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new InvalidResourceException("attribute.not.found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new InvalidResourceException("category.not.found"));

        if (request.isFilterable() && !CollectionUtils.isEmpty(request.values())) {
            attribute.getAttributeValues().clear();

            request.values().stream()
                    .map(value -> AttributeValueMapper.toEntity(value, attribute))
                    .forEach(attribute.getAttributeValues()::add);
        }

        AttributeMapper.update(attribute, request, category);
        attributeRepository.save(attribute);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!attributeRepository.existsById(id))
            throw new InvalidResourceException("attribute.not.found"); //TODO: doesn't need to throw exception. just handle manually
        attributeRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return attributeRepository.existsById(id);
    }

}
package com.atrastones.shop.mapper.implement;

import com.atrastones.shop.dto.AttributeDTO;
import com.atrastones.shop.dto.AttributeValueDTO;
import com.atrastones.shop.mapper.contract.AttributeMapper;
import com.atrastones.shop.mapper.contract.AttributeValueMapper;
import com.atrastones.shop.mapper.contract.CategoryMapper;
import com.atrastones.shop.model.entity.Attribute;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AttributeMapperImp implements AttributeMapper {

    private final CategoryMapper categoryMapper;
    private final AttributeValueMapper attributeValueMapper;

    public AttributeMapperImp(CategoryMapper categoryMapper, AttributeValueMapper attributeValueMapper) {
        this.categoryMapper = categoryMapper;
        this.attributeValueMapper = attributeValueMapper;
    }

    @Override
    public AttributeDTO toDTO(Attribute attribute) {
        return AttributeDTO.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .isFilterable(attribute.isFilterable())
                .categoryId(attribute.getCategory().getId())
                .build();
    }

    @Override
    public AttributeDTO toFullDTO(Attribute attribute) {

        Set<AttributeValueDTO> attributeValues = attribute.getAttributeValuesPivot().stream()
                .map(value->
                        attributeValueMapper.toDTO(value.getAttributeValue()))
                .collect(Collectors.toSet());

        return AttributeDTO.builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .isFilterable(attribute.isFilterable())
                .createdAt(attribute.getCreatedAt())
                .updatedAt(attribute.getUpdatedAt())
                .category(categoryMapper.toDTO(attribute.getCategory()))
                .attributeValues(attributeValues)
                .build();
    }

    @Override
    public Attribute toEntity(AttributeDTO attributeDTO) {
        return null;
    }

}

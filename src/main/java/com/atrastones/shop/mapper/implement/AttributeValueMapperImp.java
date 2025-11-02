package com.atrastones.shop.mapper.implement;

import com.atrastones.shop.dto.AttributeValueDTO;
import com.atrastones.shop.mapper.contract.AttributeValueMapper;
import com.atrastones.shop.model.entity.AttributeValue;
import org.springframework.stereotype.Component;

@Component
public class AttributeValueMapperImp implements AttributeValueMapper {

    @Override
    public AttributeValueDTO toDTO(AttributeValue attributeValue) {
        return AttributeValueDTO.builder()
                .id(attributeValue.getId())
                .value(attributeValue.getValue())
                .build();
    }

    @Override
    public AttributeValueDTO toFullDTO(AttributeValue attributeValue) {
        return AttributeValueDTO.builder()
                .id(attributeValue.getId())
                .value(attributeValue.getValue())
                .createdAt(attributeValue.getCreatedAt())
                .build();
    }

    @Override
    public AttributeValue toEntity(AttributeValueDTO attributeValueDTO) {
        return null;
    }

}

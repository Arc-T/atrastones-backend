package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.AttributeValue;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class AttributeValueDTO {

    Long id;

    String value;

    LocalDateTime createdAt;

    public static AttributeValueDTO toDTO(AttributeValue attributeValue) {
        return AttributeValueDTO.builder()
                .id(attributeValue.getId())
                .value(attributeValue.getValue())
                .build();
    }

    public static AttributeValueDTO toFullDTO(AttributeValue attributeValue) {
        return AttributeValueDTO.builder()
                .id(attributeValue.getId())
                .value(attributeValue.getValue())
                .createdAt(attributeValue.getCreatedAt())
                .build();
    }

}

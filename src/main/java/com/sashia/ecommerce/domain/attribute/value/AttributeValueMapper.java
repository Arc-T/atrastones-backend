package com.sashia.ecommerce.domain.attribute.value;

import com.sashia.ecommerce.domain.attribute.Attribute;

public class AttributeValueMapper {

    private AttributeValueMapper() {
    }

    public static AttributeValueRequest toDTO(AttributeValue attributeValue) {
        return new AttributeValueRequest(
                attributeValue.getId(),
                attributeValue.getAttributeValue(),
                attributeValue.getCreatedAt()
        );
    }

    public static AttributeValue toEntity(AttributeValueRequest dto, Attribute attribute) {
        AttributeValue attributeValue = new AttributeValue();
        attributeValue.setAttribute(attribute);
        attributeValue.setAttributeValue(dto.value());
        return attributeValue;
    }

}

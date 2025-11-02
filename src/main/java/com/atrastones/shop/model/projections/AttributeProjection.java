package com.atrastones.shop.model.projections;

import java.time.LocalDateTime;

public interface AttributeProjection {

    Long getId();

    String getName();

    Long getCategoryId();

    String getType();

    Boolean isFilterable();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    CategoryInfo getCategory();

    AttributeInfo getAttributeValue();

    interface CategoryInfo {

        Long getId();

        String getName();

    }

    interface AttributeInfo {

        Long getId();

        String getValue();
    }

}
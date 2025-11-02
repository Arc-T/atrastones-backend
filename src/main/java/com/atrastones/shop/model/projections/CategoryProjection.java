package com.atrastones.shop.model.projections;

import java.time.LocalDateTime;

public interface CategoryProjection {

    Long getId();

    String getName();

    String getUrl();

    String getIcon();

    Long getParentId();

    Long getDisplayOrder();

    String getDescription();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

}

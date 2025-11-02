package com.atrastones.shop.model.projections;

import java.time.LocalDateTime;

public interface AttributeValueProjection {

    Long getId();

    String getValue();

    LocalDateTime getCreatedAt();

}

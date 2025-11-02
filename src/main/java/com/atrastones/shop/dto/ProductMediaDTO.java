package com.atrastones.shop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class ProductMediaDTO {

    Long id;

    Long productId;

    Long mediaTypeId;

    String url;

    Integer displayOrder;

    String extension;

    LocalDateTime createdAt;

}

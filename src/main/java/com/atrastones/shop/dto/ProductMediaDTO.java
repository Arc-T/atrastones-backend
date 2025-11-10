package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.ProductMedia;
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

    String url;

    MediaTypeDto type;

    Integer displayOrder;

    String extension;

    LocalDateTime createdAt;

    // ********************** DTO **********************

    public static ProductMediaDTO toDTO(ProductMedia productMedia) {
        return ProductMediaDTO.builder()
                .id(productMedia.getId())
//                .type(MediaTypeDto.toDTO(productMedia.getMediaType()))
                .url(productMedia.getUrl())
                .displayOrder(productMedia.getDisplayOrder())
                .createdAt(productMedia.getCreatedAt())
                .build();
    }

}

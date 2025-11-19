package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Offer;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class OfferDTO {

    Long id;

    String name;

    BigDecimal cost;

    Long offerGroupId;

    String description;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDateTime deletedAt;

    public static OfferDTO toDTO(Offer offer) {
        return OfferDTO.builder()
                .id(offer.getId())
                .name(offer.getName())
                .cost(offer.getCost())
                .offerGroupId(offer.getOfferingGroup().getId())
                .description(offer.getDescription())
                .createdAt(offer.getCreatedAt())
                .updatedAt(offer.getUpdatedAt())
                .build();
    }

}

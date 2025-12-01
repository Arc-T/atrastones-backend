package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.Offer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OfferDTO(
        Long id,
        String name,
        BigDecimal cost,
        Long offerGroupId,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    // ********************** DTOs **********************
    public static OfferDTO toDTO(Offer offer) {
        return new OfferDTO(
                offer.getId(),
                offer.getName(),
                offer.getCost(),
                offer.getOfferGroup().getId(),
                offer.getDescription(),
                offer.getCreatedAt(),
                offer.getUpdatedAt(),
                offer.getDeletedAt()
        );
    }

}

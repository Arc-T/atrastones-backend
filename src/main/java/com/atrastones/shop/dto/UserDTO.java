package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.ShopMember;
import com.atrastones.shop.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long userGroupId,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) List<ShopDTO> shopsInfo,
        String gender,
        String description,
        LocalDateTime createdAt
) {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getUserGroup() != null ? user.getUserGroup().getId() : null,
                mapShops(user.getShopMembers()),
                user.getGender() != null ? user.getGender().name() : null,
                user.getDescription(),
                user.getCreatedAt()
        );
    }

    private static List<ShopDTO> mapShops(Collection<ShopMember> shopMembers) {
        if (shopMembers == null || shopMembers.isEmpty())
            return Collections.emptyList();

        return shopMembers.stream()
                .map(ShopMember::getShop)
                .filter(Objects::nonNull)
                .map(ShopDTO::toDTO)
                .toList();
    }

}
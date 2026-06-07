package com.atrastones.ecommerce.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String email,
        String phone,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Long userGroupId,
        String gender,
        String description,
        LocalDateTime createdAt
) {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.id(),
                user.email(),
                user.phone(),
                user.userGroup() != null ? user.userGroup().id() : null,
                user.gender() != null ? user.gender().name() : null,
                user.description(),
                user.createdAt()
        );
    }

}
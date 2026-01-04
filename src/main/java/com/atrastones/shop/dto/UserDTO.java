package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password,
        Long userGroupId,
        Long shopId,
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
                user.getPassword(),
                user.getUserGroup().getId(),
                (long) user.getShops().size(),
                user.getGender().name(),
                user.getDescription(),
                user.getCreatedAt()
        );
    }

}
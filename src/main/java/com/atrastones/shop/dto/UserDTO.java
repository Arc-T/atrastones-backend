package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.User;
import com.atrastones.shop.type.GenderType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        @NotNull String firstName,
        String lastName,
        String email,
        String phone,
        @JsonProperty(access = JsonProperty.Access.READ_WRITE) String password,
        Long userGroupId,
        GenderType gender,
        Long provinceId,
        String description,
        LocalDateTime createdAt
) {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirsName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword(),
                user.getUserGroup().getId(),
                user.getGender(),
                null,
                user.getDescription(),
                user.getCreatedAt()
        );
    }
}

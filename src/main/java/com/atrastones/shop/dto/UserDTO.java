package com.atrastones.shop.dto;

import com.atrastones.shop.model.entity.User;
import com.atrastones.shop.type.GenderType;
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
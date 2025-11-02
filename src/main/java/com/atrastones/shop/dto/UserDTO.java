package com.atrastones.shop.dto;

import com.atrastones.shop.type.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
public class UserDTO {

    private Long id;

    @NotNull
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String password;

    private Long groupId;

    private Gender gender;

    private Long provinceId;

    private String description;

    private LocalDateTime createdAt;

}

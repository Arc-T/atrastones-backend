package com.atrastones.shop.dto;

import com.atrastones.shop.type.LoginType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationDTO(
        @JsonProperty(access = JsonProperty.Access.READ_WRITE) String username,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Boolean hasAccount,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Boolean hasPassword,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) LoginType loginType,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) String token,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Integer smsTtl,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) UserDTO user
) {

    public AuthenticationDTO(Boolean hasAccount) {
        this(
                null,
                hasAccount,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public AuthenticationDTO(Boolean hasAccount, String token) {
        this(
                null,
                hasAccount,
                null,
                null,
                token,
                null,
                null,
                null
        );
    }

    public AuthenticationDTO(String username, String token, UserDTO user) {
        this(
                username,
                null,
                null,
                null,
                token,
                null,
                null,
                user
        );
    }

    public AuthenticationDTO(Integer smsTtl, Boolean hasAccount, String username) {
        this(
                username,
                hasAccount,
                null,
                null,
                null,
                null,
                smsTtl,
                null
        );
    }

}
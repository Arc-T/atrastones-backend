package com.atrastones.ecommerce.authentication.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationDTO(
        @JsonProperty(access = JsonProperty.Access.READ_WRITE) String username,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Boolean hasAccount,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Boolean hasPassword,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) LoginType loginType,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) String token,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY) Integer smsTtl
) {

    public AuthenticationDTO(Boolean hasAccount) {
        this(
                null,
                hasAccount,
                null,
                null,
                null,
                null,
                null
        );
    }

    public AuthenticationDTO(String username, String token) {
        this(
                username,
                null,
                null,
                null,
                null,
                token,
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
                null
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
                smsTtl
        );
    }

}
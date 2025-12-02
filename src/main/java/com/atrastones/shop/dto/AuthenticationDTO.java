package com.atrastones.shop.dto;

import com.atrastones.shop.type.LoginType;

public record AuthenticationDTO(
        String username,
        Boolean hasAccount,
        Boolean hasPassword,
        LoginType loginType,
        String token,
        Integer smsTtl,
        String password
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
                token,
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
                smsTtl,
                null
        );
    }

}
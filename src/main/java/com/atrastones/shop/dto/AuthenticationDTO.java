package com.atrastones.shop.dto;

import com.atrastones.shop.type.LoginType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class AuthenticationDTO {

    String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Boolean hasAccount;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Boolean hasPassword;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    LoginType loginType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String token;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer smsTtl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

}
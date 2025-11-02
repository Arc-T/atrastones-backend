package com.atrastones.shop.config;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails extends User {

    private final Long id;

    private final String firstName;

    private final String lastName;

    private final String token;

    private final String tokenType;

    public CustomUserDetails(UserDetails user, Long id, String firstName, String lastName, String token, String tokenType) {
        super(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.tokenType = tokenType;
    }

}

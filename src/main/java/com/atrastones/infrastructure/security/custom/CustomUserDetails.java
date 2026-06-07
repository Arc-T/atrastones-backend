package com.atrastones.infrastructure.security.custom;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends User {

    private final Long id;
    private final String firstName;
    private final String lastName;

    public CustomUserDetails(UserDetails user, Long id, String firstName, String lastName) {
        super(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
        this.id = id;
        user.getUsername();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /* **************************** GETTERS **********************************/

    public Long id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

}

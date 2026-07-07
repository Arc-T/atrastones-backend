package com.sashia.ecommerce.configuration;

import com.sashia.ecommerce.domain.user.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component(value = "userDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        return userRepository.findByPhoneWithAuthorities(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
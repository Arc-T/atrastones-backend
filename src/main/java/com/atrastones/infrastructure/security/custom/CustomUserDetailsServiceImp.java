package com.atrastones.infrastructure.security.custom;

import com.atrastones.ecommerce.authentication.internal.AuthUserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class CustomUserDetailsServiceImp implements CustomUserDetailsService {

    private final AuthUserRepository userRepository;

    CustomUserDetailsServiceImp(AuthUserRepository authUserRepository) {
        this.userRepository = authUserRepository;
    }

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        return userRepository.findByPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
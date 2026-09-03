package com.sashia.ecommerce.platform.security;

import com.sashia.ecommerce.identity.user.User;
import com.sashia.ecommerce.identity.user.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        User user = userRepository.findByPhoneWithAuthorities(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));


    }

}
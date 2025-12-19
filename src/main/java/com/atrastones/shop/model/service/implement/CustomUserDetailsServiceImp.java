package com.atrastones.shop.model.service.implement;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import com.atrastones.shop.model.repository.contract.UserRepository;
import com.atrastones.shop.model.service.contract.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsServiceImp implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.getByPhone(username);
    }

}
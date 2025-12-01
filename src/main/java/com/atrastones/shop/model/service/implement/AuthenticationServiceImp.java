package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.AuthenticationDTO;
import com.atrastones.shop.dto.SmsDTO;
import com.atrastones.shop.model.service.contract.AuthenticationService;
import com.atrastones.shop.model.service.contract.SmsService;
import com.atrastones.shop.model.service.contract.UserService;
import com.atrastones.shop.type.LoginType;
import com.atrastones.shop.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private final SmsService smsService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImp(UserService userService, AuthenticationManager authenticationManager, SmsService smsService, UserDetailsService userDetailsService) {
        this.smsService = smsService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationDTO authenticateUser(AuthenticationDTO authentication, String panel) {
        return switch (panel) {
            case "customer" -> {
                if (authentication.getLoginType().equals(LoginType.SMS)) {
                    yield AuthenticationDTO.builder()
                            .smsTtl(smsService.getOrCreateTtl(authentication.getUsername()))
                            .hasAccount(userService.existsByPhone(authentication.getUsername()))
                            .username(authentication.getUsername())
                            .build();
                } else if (authentication.getLoginType().equals(LoginType.PASSWORD)) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authentication.getUsername(), authentication.getPassword()));
                } else if (authentication.getLoginType().equals(LoginType.EMAIL)) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authentication.getUsername(), authentication.getPassword()));
                }
                throw new IllegalStateException("Invalid login type");
            }
            case "admin" -> attemptWithPassword(authentication.getUsername(), authentication.getPassword());
            default -> throw new IllegalStateException("Unexpected value: " + panel);
        };
    }

    @Override
    public AuthenticationDTO attemptWithOtp(String phone, Integer otpCode) {
        Optional<SmsDTO> sms = smsService.getPhoneLatestSmsMessage(phone);
        boolean checkOtp = sms.isPresent() && Integer.parseInt(sms.get().description()) == otpCode;
        if (checkOtp && userService.existsByPhone(phone)) {
            return AuthenticationDTO.builder()
                    .hasAccount(true)
                    .token(JwtUtils.generateToken(userDetailsService.loadUserByUsername(phone)))
                    .build();
        }
        else if (checkOtp) {
            return AuthenticationDTO.builder()
                    .hasAccount(false)
                    .build();
        } else throw new UsernameNotFoundException("Invalid phone number");
    }

    @Override
    public boolean checkTokenValidity(String token) {
        return JwtUtils.isTokenValid(token, userDetailsService.loadUserByUsername(JwtUtils.extractUsername(token)));
    }

    private AuthenticationDTO attemptWithPassword(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username, password
                )
        );
        return AuthenticationDTO.builder()
                .username(username)
                .token(JwtUtils.generateToken(userDetailsService.loadUserByUsername(username)))
                .build();
    }

}
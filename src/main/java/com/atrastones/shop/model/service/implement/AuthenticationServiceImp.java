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
                if (authentication.loginType().equals(LoginType.SMS)) {
                    yield new AuthenticationDTO(
                            smsService.getOrCreateTtl(authentication.username()),
                            userService.existsByPhone(authentication.username()),
                            authentication.username()
                    );
                } else if (authentication.loginType().equals(LoginType.PASSWORD)) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authentication.username(), authentication.password()));
                } else if (authentication.loginType().equals(LoginType.EMAIL)) {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authentication.username(), authentication.password()));
                }
                throw new IllegalStateException("Invalid login type");
            }
            case "admin" -> attemptWithPassword(authentication.username(), authentication.password());
            default -> throw new IllegalStateException("Unexpected value: " + panel);
        };
    }

    @Override
    public AuthenticationDTO attemptWithOtp(String phone, Integer otpCode) {
        Optional<SmsDTO> sms = smsService.getPhoneLatestSmsMessage(phone);
        boolean checkOtp = sms.isPresent() && Integer.parseInt(sms.get().description()) == otpCode;
        if (checkOtp && userService.existsByPhone(phone)) {
            return new AuthenticationDTO(
                    true,
                    JwtUtils.generateToken(userDetailsService.loadUserByUsername(phone))
            );
        } else if (checkOtp) {
            return new AuthenticationDTO(false);
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
        return new AuthenticationDTO(
                username,
                JwtUtils.generateToken(userDetailsService.loadUserByUsername(username))
        );
    }

}
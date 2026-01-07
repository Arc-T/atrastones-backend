package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.AuthenticationDTO;
import com.atrastones.shop.dto.SmsDTO;
import com.atrastones.shop.dto.UserDTO;
import com.atrastones.shop.model.service.contract.AuthenticationService;
import com.atrastones.shop.model.service.contract.CustomUserDetailsService;
import com.atrastones.shop.model.service.contract.SmsService;
import com.atrastones.shop.model.service.contract.UserService;
import com.atrastones.shop.utils.JwtUtils;
import com.atrastones.shop.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final SmsService smsService;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public AuthenticationDTO authenticateAdmin(AuthenticationDTO authentication) {
        UserDetails user = customUserDetailsService.loadUserByUsername(authentication.username());
        SecurityUtils.setUser(authentication.username(), user.getAuthorities());
        UserDTO userInfo = userService.loadByPhone(authentication.username());
        return new AuthenticationDTO(
                authentication.username(),
                JwtUtils.generateToken(user),
                userInfo
        );
    }

    @Override
    public AuthenticationDTO authenticateCustomer(AuthenticationDTO authentication) {
        return new AuthenticationDTO(
                smsService.getOrCreateTtl(authentication.username()),
                userService.existsByPhone(authentication.username()),
                authentication.username()
        );
    }

    @Override
    public AuthenticationDTO attemptWithOtp(String phone, Integer otpCode) {
        Optional<SmsDTO> sms = smsService.getPhoneLatestSmsMessage(phone);
        boolean checkOtp = sms.isPresent() && Integer.parseInt(sms.get().description()) == otpCode;
        if (checkOtp && userService.existsByPhone(phone)) {
            return new AuthenticationDTO(
                    true,
                    JwtUtils.generateToken(customUserDetailsService.loadUserByUsername(phone))
            );
        } else if (checkOtp) {
            return new AuthenticationDTO(false);
        } else throw new UsernameNotFoundException("Invalid phone number");
    }

    @Override
    public boolean checkTokenValidity(String token) {
        return JwtUtils.isTokenValid(token,
                customUserDetailsService.loadUserByUsername(JwtUtils.extractUsername(token))
        );
    }

}
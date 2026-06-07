package com.atrastones.ecommerce.authentication.internal;

import com.atrastones.ecommerce.authentication.common.AuthenticationDTO;
import com.atrastones.ecommerce.authentication.common.LoginDTO;
import com.atrastones.infrastructure.security.JwtUtils;
import com.atrastones.infrastructure.security.SecurityUtils;
import com.atrastones.notification.common.SMSDTO;
import com.atrastones.notification.common.SMSEventDTO;
import com.atrastones.notification.common.SMSService;
import com.atrastones.notification.common.SMSType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AuthenticationServiceImp implements AuthenticationService {

    private final SMSService smsService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AuthenticationServiceImp(SMSService smsService, AuthenticationManager authenticationManager,
                                    ApplicationEventPublisher applicationEventPublisher) {
        this.smsService = smsService;
        this.authenticationManager = authenticationManager;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public AuthenticationDTO authenticateAdmin(LoginDTO credentials) {
        Authentication userAuth = attemptByPassword(credentials.username(), credentials.password());
        SecurityUtils.setUser(credentials.username(), userAuth.getAuthorities());
        return new AuthenticationDTO(
                credentials.username(),
                JwtUtils.generateToken(SecurityUtils.getCurrentUser())
        );
    }

    @Override
    public AuthenticationDTO authenticateCustomer(AuthenticationDTO authentication) {
        return new AuthenticationDTO(
                120,
//                smsService.getOrCreateTtl(authentication.username()),
                false,
//                userService.existsByPhone(authentication.username()), //TODO
                authentication.username()
        );
    }

    @Override
    public AuthenticationDTO attemptWithOTP(String phone, Integer otpCode) {
        Optional<SMSDTO> sms = smsService.getPhoneLatestSmsMessage(phone);
        boolean checkOtp = sms.isPresent() && Integer.parseInt(sms.get().description()) == otpCode;
        if (checkOtp) { //TODO && userService.existsByPhone(phone)
            return new AuthenticationDTO(
                    true,
                    JwtUtils.generateToken(SecurityUtils.getCurrentUser())
            );
        } else if (checkOtp) {
            applicationEventPublisher.publishEvent(new SMSEventDTO("test", SMSType.OTP, otpCode.toString()));
            return new AuthenticationDTO(false);
        } else throw new UsernameNotFoundException("Invalid phone number");
    }

    // =================================== HELPERS ===================================

    private void attemptBySmsToken(String token) {
        authenticationManager.authenticate(
                new OneTimeTokenAuthenticationToken(
                        token
                )
        );
    }

    private Authentication attemptByPassword(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
    }

}
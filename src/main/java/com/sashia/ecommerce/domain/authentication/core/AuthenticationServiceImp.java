package com.sashia.ecommerce.domain.authentication.core;

import com.sashia.ecommerce.domain.authentication.common.AuthenticationDTO;
import com.sashia.ecommerce.domain.authentication.common.AuthenticationResponse;
import com.sashia.ecommerce.domain.authentication.common.LoginRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import static com.sashia.ecommerce.common.util.SecurityUtils.*;

@Service
class AuthenticationServiceImp implements AuthenticationService {

    private static final long DEFAULT_EXPIRATION_TIME = 3_600_000L; // 1 hour.
    private static final long REMEMBER_ME_EXPIRATION_TIME = 2_592_000_000L; // 1 month

    private final JwtEncoder jwtEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthenticationServiceImp(JwtEncoder jwtEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, ApplicationEventPublisher applicationEventPublisher) {
        this.jwtEncoder = jwtEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse authenticateAdmin(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new AuthenticationResponse(generateToken(authentication, request.rememberMe()));
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
//        Optional<SMSDTO> sms = smsService.getPhoneLatestSmsMessage(phone);
//        boolean checkOtp = sms.isPresent() && Integer.parseInt(sms.get().description()) == otpCode;
//        if (checkOtp) { //TODO && userService.existsByPhone(phone)
//            return new AuthenticationDTO(
//                    true,
//                    JwtUtils.generateToken(SecurityUtils.getCurrentUser())
//            );
//        } else if (checkOtp) {
//            applicationEventPublisher.publishEvent(new SMSEventDTO("test", SMSType.OTP, otpCode.toString()));
//            return new AuthenticationDTO(false);
//        } else throw new UsernameNotFoundException("Invalid phone number");
        return null;
    }

    // =================================== HELPERS ===================================

    private String generateToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(REMEMBER_ME_EXPIRATION_TIME, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(DEFAULT_EXPIRATION_TIME, ChronoUnit.SECONDS);
        }

        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim(AUTHORITIES_CLAIM, authorities);

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, builder.build())).getTokenValue();
    }

}
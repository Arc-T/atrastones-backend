package com.atrastones.shop.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockOidcUserSecurityContextFactory implements WithSecurityContextFactory<WithMockBearerTokenUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockBearerTokenUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        return null;
//        CustomUserDetails cud = new CustomUserDetails(
//                User.withUsername(annotation.username())
//                        .password("password")
//                        .authorities(annotation.authorities())
//                        .build()
//                , UUID.randomUUID()
//                , UserInfo.builder()
//                .firstName(annotation.firstName())
//                .lastName(annotation.lastName())
//                .birthdate(annotation.birthdate())
//                .fatherName(annotation.fatherName())
//                .gender(annotation.gender())
//                .mobile("09132112112")
//                .email("test@test.com")
//                .build()
//                , "token", "Bearer", "chamber");
//
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(cud, "password", cud.getAuthorities());
//        context.setAuthentication(authentication);
//        return context;
    }

}
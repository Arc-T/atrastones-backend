package com.atrastones.shop.utils;

import com.atrastones.shop.config.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentUserUsername() {
        return getCurrentUser().getUsername();
    }

    public static String getCurrentUserFirstName() {
        return getCurrentUser().getFirstName();
    }

    public static String getCurrentUserLastName() {
        return getCurrentUser().getLastName();
    }

    public static CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Collection<GrantedAuthority> getAuthorities() {
        CustomUserDetails principal =
                (CustomUserDetails) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        return principal.getAuthorities();
    }

    public static boolean isAnonymous() {
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        return principal instanceof String && principal.equals("anonymousUser");
    }

    public static String getToken() {
        return getCurrentUser().getToken();
    }

    public static String getTokenType() {
        return getCurrentUser().getTokenType();
    }

    public static void setUser(String username, Collection<? extends GrantedAuthority> authorities) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (!isSecurityContextEmpty()) {
            throw new IllegalStateException("Security context is already authenticated with user: " + existingAuth.getName() + ". Cannot override existing authentication.");
        } else {
            CustomUserDetails userDetails = createUser(username, authorities);
            Authentication authentication = createAuthentication(authorities, userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private static Authentication createAuthentication(Collection<? extends GrantedAuthority> authorities, CustomUserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    public static void setUser(String username) {
        setUser(username, Collections.emptyList());
    }

    private static CustomUserDetails createUser(String username, Collection<? extends GrantedAuthority> authorities) {
        return new CustomUserDetails(User.withUsername(username).password("").authorities(authorities).build(), null, "", "", "", "");
    }

    private static boolean isSecurityContextEmpty() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null || !auth.isAuthenticated() || isAnonymous();
    }

}

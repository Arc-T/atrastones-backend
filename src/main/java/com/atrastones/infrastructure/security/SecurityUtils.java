package com.atrastones.infrastructure.security;

import com.atrastones.infrastructure.security.custom.CustomUserDetails;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    // ============================ GETTERS ============================

    public static Long getCurrentUserId() {
        return getCurrentUser().id();
    }

    public static String getCurrentUserLastName() {
        return getCurrentUser().lastName();
    }

    public static String getCurrentUserUsername() {
        return getCurrentUser().getUsername();
    }

    public static String getCurrentUserFirstName() {
        return getCurrentUser().firstName();
    }

    public static CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) Objects.requireNonNull(getAuthenticationFromContext()).getPrincipal();
    }

    public static Collection<GrantedAuthority> getAuthorities() {
        CustomUserDetails principal = (CustomUserDetails) getAuthenticationFromContext().getPrincipal();
        return (principal != null && !CollectionUtils.isEmpty(principal.getAuthorities()))
                ? principal.getAuthorities() : Collections.emptyList();
    }

    // ============================ FUNCTIONS =========================================

    public static boolean isAnonymous() {
        Object principal = Objects.requireNonNull(getAuthenticationFromContext()).getPrincipal();
        return principal instanceof String && principal.equals("anonymousUser");
    }

    public static void setUser(String username) {
        setUser(username, Collections.emptyList());
    }

    public static void setUser(String username, Collection<? extends GrantedAuthority> authorities) {
        Authentication existingAuth = getAuthenticationFromContext();
        if (!isSecurityContextEmpty()) {
            throw new IllegalStateException("Security context is already authenticated with user: " + existingAuth.getName() + ". Cannot override existing authentication.");
        } else {
            CustomUserDetails userDetails = createUser(username, authorities);
            Authentication authentication = createAuthentication(authorities, userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    // ================================== HELPERS ====================================

    private static boolean isSecurityContextEmpty() {
        Authentication auth = getAuthenticationFromContext();
        return auth == null || !auth.isAuthenticated() || isAnonymous();
    }

    private static Authentication getAuthenticationFromContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static CustomUserDetails createUser(String username, Collection<? extends GrantedAuthority> authorities) {
        return new CustomUserDetails(User.withUsername(username).password("").authorities(authorities).build(), null, "", "");
    }

    private static Authentication createAuthentication(Collection<? extends GrantedAuthority> authorities, CustomUserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

}

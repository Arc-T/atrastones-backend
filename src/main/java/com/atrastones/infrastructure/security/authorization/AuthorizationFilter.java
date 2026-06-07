package com.atrastones.infrastructure.security.authorization;

import com.atrastones.infrastructure.security.JwtUtils;
import com.atrastones.infrastructure.security.SecurityUtils;
import com.atrastones.infrastructure.security.custom.CustomUserDetailsService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String TOKEN_COOKIE = "token";

    private final CustomUserDetailsService userDetailsService;

    public AuthorizationFilter(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Optional<String> tokenOpt = extractToken(request);

        if (tokenOpt.isEmpty() || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = tokenOpt.get();
            String username = JwtUtils.extractUsername(token);

            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (JwtUtils.isTokenValid(token, userDetails)) {
                SecurityUtils.setUser(username, userDetails.getAuthorities());
            }

        } catch (JWTVerificationException ex) {
            clearTokenCookie(response);
            request.setAttribute("isExpired", true);
        }

        filterChain.doFilter(request, response);
    }

    // ============================== HELPERS ==============================

    private void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_COOKIE, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(c -> TOKEN_COOKIE.equals(c.getName()))
                .map(Cookie::getValue)
                .filter(v -> !v.isBlank())
                .findFirst();
    }

}
package com.atrastones.infrastructure.security;

import com.atrastones.infrastructure.security.authorization.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManagerFactory;
import org.springframework.security.authorization.DefaultAuthorizationManagerFactory;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityContextConfig {

    private final AuthorizationFilter authorizationFilter;

    public SecurityContextConfig(AuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(config -> config.configurationSource(customCorsConfiguration()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/actuator/**",
                                "/products/**",
                                "/authentication/**",
                                "/authentication"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authorizationFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public <T> AuthorizationManagerFactory<T> authorizationManagerFactory() {
        DefaultAuthorizationManagerFactory<T> authorizationManagerFactory = new DefaultAuthorizationManagerFactory<>();
        authorizationManagerFactory.setRoleHierarchy(customRoleHierarchy());
        return authorizationManagerFactory;
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setAuthorizationManagerFactory(authorizationManagerFactory());
        return expressionHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    // =============================== HELPERS ===============================

    private RoleHierarchy customRoleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("SHOP_OWNER")
                .role("SHOP_OWNER").implies("GUEST")
                .build();
    }

    private CorsConfigurationSource customCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        // ✅ Exact frontend URLs (cannot use "*") because we send cookies
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",     // React dev server
                "https://atrastones.com"     // production
        ));
        configuration.setAllowCredentials(true); // very important for cookies
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        // Optional: expose headers to client if needed
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
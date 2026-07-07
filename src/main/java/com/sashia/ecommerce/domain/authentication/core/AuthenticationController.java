package com.sashia.ecommerce.domain.authentication.core;

import com.sashia.ecommerce.common.util.SecurityUtils;
import com.sashia.ecommerce.domain.authentication.common.AuthenticationResponse;
import com.sashia.ecommerce.domain.authentication.common.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/authentication")
class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest credentials) {
        log.error("_____________________ LOG IN _____________________");
        return ResponseEntity.ok(authenticationService.authenticateAdmin(credentials));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie refreshCookie = new Cookie("token", null);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);
        refreshCookie.setAttribute("SameSite", "Strict");
        response.addCookie(refreshCookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    @PreAuthorize("hasRole('SHOP_OWNER')")
    ResponseEntity<Boolean> validate() {
        log.error("_____________________ VALIDATION START _____________________");
        log.error("Authorities: {}", SecurityUtils.getAuthorities(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication())));
        log.error("User: {}", SecurityUtils.getCurrentUserJWT());
        log.error("Login: {}", SecurityUtils.getCurrentUserLogin());
        return ResponseEntity.ok(true);
    }

//    @PostMapping("/otp")
//     ResponseEntity<Boolean> login(@RequestBody AuthenticationDTO authentication) {
//        return ResponseEntity.ok(authenticationServiceContract.authenticateUser(authentication));
//    }
//
//    @PostMapping("/email")
//     ResponseEntity<Boolean> login(@RequestBody AuthenticationDTO authentication) {
//        return ResponseEntity.ok(authenticationServiceContract.authenticateUser(authentication));
//    }
//
//    @PostMapping("/password")
//     ResponseEntity<Boolean> login(@RequestBody AuthenticationDTO authentication) {
//        return ResponseEntity.ok(authenticationServiceContract.authenticateUser(authentication));
//    }

}

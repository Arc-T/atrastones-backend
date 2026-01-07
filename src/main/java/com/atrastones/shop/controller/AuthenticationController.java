package com.atrastones.shop.controller;

import com.atrastones.shop.dto.AuthenticationDTO;
import com.atrastones.shop.model.service.contract.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationDTO> authenticateUser(@RequestBody AuthenticationDTO authentication,
                                                              HttpServletResponse response) {

        AuthenticationDTO authUser = authenticationService.authenticateAdmin(authentication);
        Cookie refreshCookie = new Cookie("token", authUser.token());
        refreshCookie.setHttpOnly(true); // Prevents XSS access
        refreshCookie.setSecure(true); // HTTPS only (enforce in prod)
        refreshCookie.setPath("/"); // App-wide access
        refreshCookie.setMaxAge(7 * 24 * 60 * 600); // 7 days in seconds
        refreshCookie.setAttribute("SameSite", "Strict");  // Mitigates CSRF
        response.addCookie(refreshCookie);

        return ResponseEntity.ok(authUser);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> checkAuth(@CookieValue(value = "token") String token,
                                       HttpServletResponse response) {
        boolean valid = token != null && authenticationService.checkTokenValidity(token);
        if (!valid) {
            Cookie cookie = new Cookie("token", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(valid);
    }

//    @PostMapping("/otp")
//    public ResponseEntity<Boolean> login(@RequestBody AuthenticationDTO authentication) {
//        return ResponseEntity.ok(authenticationServiceContract.authenticateUser(authentication));
//    }
//
//    @PostMapping("/email")
//    public ResponseEntity<Boolean> login(@RequestBody AuthenticationDTO authentication) {
//        return ResponseEntity.ok(authenticationServiceContract.authenticateUser(authentication));
//    }
//
//    @PostMapping("/password")
//    public ResponseEntity<Boolean> login(@RequestBody AuthenticationDTO authentication) {
//        return ResponseEntity.ok(authenticationServiceContract.authenticateUser(authentication));
//    }

}

package com.atrastones.shop.controller;

import com.atrastones.shop.dto.AuthenticationDTO;
import com.atrastones.shop.handler.ApiExceptionHandler;
import com.atrastones.shop.model.service.contract.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationDTO> authenticateUser(@RequestBody AuthenticationDTO authentication, HttpServletResponse response) {

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
    public ResponseEntity<?> checkAuth(@CookieValue(value = "token", required = false) String token) {
        if (StringUtils.hasText(token)) {
            if (!authenticationService.checkTokenValidity(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiExceptionHandler.ApiErrorResponse("INVALID.TOKEN", null, LocalDateTime.now()));
            }
            return ResponseEntity.ok(true);
        } else
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiExceptionHandler.ApiErrorResponse("FORBIDDEN.ACCESS", null, LocalDateTime.now()));
    }

//            Cookie cookie = new Cookie("token", null);
//            cookie.setPath("/");
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge(0);
//            response.addCookie(cookie);

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

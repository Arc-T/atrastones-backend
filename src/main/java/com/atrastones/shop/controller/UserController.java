package com.atrastones.shop.controller;

import com.atrastones.shop.model.service.contract.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth")
//    @PreAuthorize("hasAnyAuthority({'TEST_PERMISSION'})")
    public ResponseEntity<String> authenticateUser() {
        return ResponseEntity.ok("HELLO");
    }

}
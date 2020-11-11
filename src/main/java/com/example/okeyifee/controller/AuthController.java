package com.example.okeyifee.controller;

import com.example.okeyifee.dto.ProfileDTO;
import com.example.okeyifee.dto.UserDTO;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    @Autowired
    AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/confirmreset/{token}")
    public ResponseEntity<ApiResponse> confirmPasswordReset(@PathVariable() String token) {
        return authService.verifyToken(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> createAccount(@Valid @RequestBody ProfileDTO profileData) {
        return authService.createAccount(profileData);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> createAuthenticationToken(@Valid @RequestBody UserDTO authenticationRequest) {
        return authService.login(authenticationRequest);
    }
}

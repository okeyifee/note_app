package com.example.okeyifee.controller;

//import com.decagon.kindredhair.dto.AuthRequestDTO;
//import com.decagon.kindredhair.dto.ProfileDTO;
//import com.decagon.kindredhair.payload.ApiResponse;
//import com.decagon.kindredhair.service.AuthService;
import com.example.okeyifee.dto.AuthRequestDTO;
import com.example.okeyifee.dto.ProfileDTO;
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
    public ResponseEntity<ApiResponse> createHairProfile(@Valid @RequestBody ProfileDTO profileData) {
        return authService.createAccount(profileData);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> createAuthenticationToken(@Valid @RequestBody AuthRequestDTO authenticationRequest) {
        return authService.login(authenticationRequest);
    }
}

package com.example.okeyifee.service;

import com.example.okeyifee.dto.ProfileDTO;
import com.example.okeyifee.dto.UserDTO;
import com.example.okeyifee.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public interface AuthService{
    ResponseEntity<ApiResponse> verifyToken(String token);

    ResponseEntity<ApiResponse> createAccount(ProfileDTO profileData);

    ResponseEntity<ApiResponse> login(@Valid UserDTO authenticationRequest);
}

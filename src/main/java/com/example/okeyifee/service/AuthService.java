package com.example.okeyifee.service;


import com.example.okeyifee.dto.AuthRequestDTO;
import com.example.okeyifee.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface AuthService{
    ResponseEntity<Object> verifyToken(String token);

    ResponseEntity<Object> createHairProfile(UserDto profileData, BindingResult result);

    ResponseEntity<Object> createAuthToken(AuthRequestDTO authenticationRequest, BindingResult result);
}

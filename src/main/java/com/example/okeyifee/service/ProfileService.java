package com.example.okeyifee.service;

import com.example.okeyifee.dto.ActivationLinkDTO;
import com.example.okeyifee.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface ProfileService{

    ResponseEntity<ApiResponse> createActivationLink(ActivationLinkDTO activationLinkDTO);
}

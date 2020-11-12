package com.example.okeyifee.service;

import com.example.okeyifee.dto.EmailLinkDTO;
import com.example.okeyifee.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserEmailService{

    ResponseEntity<ApiResponse> sendEmailLink(EmailLinkDTO emailLinkDTO);
}

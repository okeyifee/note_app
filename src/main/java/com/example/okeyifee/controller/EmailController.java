package com.example.okeyifee.controller;

import com.example.okeyifee.dto.EmailLinkDTO;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.service.UserEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class EmailController{

    UserEmailService userEmailService;

    @Autowired
    public EmailController(UserEmailService userEmailService) {
        this.userEmailService = userEmailService;
    }
    /**
     * Method to send Account Activation link
     * @return ResponseEntity<>(response, HttpStatus)
     */
    @PostMapping("/activate")
    public ResponseEntity<ApiResponse> sendRecommendationLink(
            @Valid @RequestBody EmailLinkDTO emailLinkDTO
        ) {
        return userEmailService.sendEmailLink(emailLinkDTO);
    }
}

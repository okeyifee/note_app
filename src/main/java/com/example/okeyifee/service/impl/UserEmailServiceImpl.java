package com.example.okeyifee.service.impl;

import com.example.okeyifee.dto.EmailLinkDTO;
import com.example.okeyifee.mail.MailingService;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.service.UserEmailService;
import com.example.okeyifee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.okeyifee.utils.BuildResponse.buildResponse;

@Service
public class UserEmailServiceImpl implements UserEmailService{

    @Qualifier("mailGun")
    MailingService mailingService;
    UserService userService;

    @Autowired
    public UserEmailServiceImpl(MailingService mailingService, UserService userService) {
        this.mailingService = mailingService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ApiResponse> sendEmailLink(EmailLinkDTO emailLinkDTO) {
        userService.findByEmail(emailLinkDTO.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User with email: " + emailLinkDTO.getEmail() + "not found")
        );

        boolean isSuccessful = mailingService.sendEmailLink(
                emailLinkDTO.getEmail(),
                emailLinkDTO.getLink()
            );
        if (isSuccessful){
            ApiResponse response = new ApiResponse(HttpStatus.OK);
            return buildResponse(response);
        }
        ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        return buildResponse(response);
    }
}

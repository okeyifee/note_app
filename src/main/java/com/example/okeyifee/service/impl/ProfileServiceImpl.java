package com.example.okeyifee.service.impl;

import com.example.okeyifee.dto.ActivationLinkDTO;
import com.example.okeyifee.mail.MailingService;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.service.ProfileService;
import com.example.okeyifee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.okeyifee.utils.BuildResponse.buildResponse;


@Service
public class ProfileServiceImpl implements ProfileService{
    @Qualifier("mailGun")
    MailingService mailingService;
    UserService userService;

    @Autowired
    public ProfileServiceImpl(MailingService mailingService, UserService userService) {
        this.mailingService = mailingService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ApiResponse> createRecommendationLink(ActivationLinkDTO activationLinkDTO) {
        userService.retrieveUserByEmail(activationLinkDTO.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User with email: " + activationLinkDTO.getEmail() + "not found")
        );

        boolean isSuccessful = mailingService.sendEmailLink(
                activationLinkDTO.getEmail(),
                activationLinkDTO.getLink()
            );
        if (isSuccessful){
            ApiResponse response = new ApiResponse(HttpStatus.OK);
            return buildResponse(response);
        }
        ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        return buildResponse(response);
    }
}

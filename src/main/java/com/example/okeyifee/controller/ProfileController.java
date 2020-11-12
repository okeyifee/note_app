package com.example.okeyifee.controller;

import com.example.okeyifee.dto.ActivationLinkDTO;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProfileController {
    ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    /**
     * Method to send Recommendation link
     * @return ResponseEntity<>(response, HttpStatus)
     */
    @PostMapping("/Activate")
    public ResponseEntity<ApiResponse> sendActivationLink(
            @Valid @RequestBody ActivationLinkDTO activationLinkDTO
        ) {
        return profileService.createActivationLink(activationLinkDTO);
    }
}

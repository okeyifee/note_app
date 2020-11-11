package com.example.okeyifee.service;

import com.example.okeyifee.dto.ActivationLinkDTO;
import com.example.okeyifee.mail.MailingService;
import com.example.okeyifee.models.User;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
class ProfileServiceTest{
    ProfileServiceImpl profileService;
    ActivationLinkDTO recommendationLinkDTO;
    User user;

    @MockBean
    MailingService mailingService;

    @MockBean
    UserService userService;

    @BeforeEach
    void setUp() {
        profileService = new ProfileServiceImpl(mailingService, userService);

        recommendationLinkDTO = new ActivationLinkDTO();
        recommendationLinkDTO.setEmail("email@test.com");
        recommendationLinkDTO.setLink("link/test.com");

        user = new User();
    }

    @Test
    void testCreateActivationLink() {
        when(mailingService.sendEmailLink(anyString(), anyString())).thenReturn(true);
        when(userService.retrieveUserByEmail(recommendationLinkDTO.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse> response = profileService.createActivationLink(recommendationLinkDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateActivationLinkOnResponseError() {
        when(mailingService.sendEmailLink(anyString(), anyString())).thenReturn(false);
        when(userService.retrieveUserByEmail(recommendationLinkDTO.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<ApiResponse> response = profileService.createActivationLink(recommendationLinkDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateActivationLinkOnInvalidUser() {
        when(userService.retrieveUserByEmail(recommendationLinkDTO.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> profileService.createActivationLink(recommendationLinkDTO));
    }
}
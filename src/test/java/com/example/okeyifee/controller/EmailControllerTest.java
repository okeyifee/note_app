package com.example.okeyifee.controller;

import com.example.okeyifee.dto.EmailLinkDTO;
import com.example.okeyifee.mail.MailingService;
import com.example.okeyifee.models.User;
import com.example.okeyifee.service.UserEmailService;
import com.example.okeyifee.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@AutoConfigureMockMvc
class EmailControllerTest{
    EmailLinkDTO recommendationLinkDTO;
    User user;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MailingService mailingService;

    @MockBean
    UserService userService;

    @MockBean
    UserEmailService userService2;

    @BeforeEach
    void setUp() {
        recommendationLinkDTO = new EmailLinkDTO();
        recommendationLinkDTO.setEmail("email@test.com");
        recommendationLinkDTO.setLink("link/test.com");
        user = new User();
    }

    @Test
    void sendActivationLink() throws Exception {
        when(mailingService.sendEmailLink(anyString(), anyString())).thenReturn(true);
        when(userService.retrieveUserByEmail(recommendationLinkDTO.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/Activate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(recommendationLinkDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Send Activation link method returns appropriate response for error")
    void sendActivationLinkOnError() throws Exception {
        when(mailingService.sendEmailLink(anyString(), anyString())).thenReturn(false);
        when(userService.retrieveUserByEmail(recommendationLinkDTO.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/Activate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(recommendationLinkDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Send recommendation link method returns appropriate response if user is not registered")
    void sendRecommendationLinkOnInvalidUser() throws Exception {
        when(userService.retrieveUserByEmail(recommendationLinkDTO.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/Activate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(recommendationLinkDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error")
                        .value("User with email: " + recommendationLinkDTO.getEmail() + "not found"));
    }
}
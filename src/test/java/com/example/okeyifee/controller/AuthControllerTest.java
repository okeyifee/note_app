package com.example.okeyifee.controller;

import com.example.okeyifee.dto.AuthRequestDTO;
import com.example.okeyifee.dto.ProfileDTO;
import com.example.okeyifee.dto.UserDTO;
import com.example.okeyifee.models.User;
import com.example.okeyifee.security.JwtTokenProvider;
import com.example.okeyifee.service.impl.UserServiceImpl;
import com.example.okeyifee.utils.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserServiceImpl userService;

    @Test
    void testCreateHairAccountWithInvalidInput() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail("okeyife");
        profileDTO.setPassword("dgddhjdjhd");
        profileDTO.setPhoneNumber("070******41");
        profileDTO.setUsername("");

        doNothing().when(userService).saveUserData(any(ProfileDTO.class));

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(profileDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error")
                        .value("Validation Error" ));

    }

    @Test
    void testConfirmPasswordReset() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setUsername("sammy");
        user.setPassword(encoder.encode("password"));
        String token = tokenProvider.createForgotPasswordToken(user.getEmail());

        when(userService.retrieveUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/auth/confirmreset/" + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Valid token"));
    }

    @Test
    void testConfirmPasswordResetWithInvalidUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword(encoder.encode("password"));
        String token = tokenProvider.createForgotPasswordToken(user.getEmail());

        when(userService.retrieveUserByEmail(user.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(get("/auth/confirmreset/" + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error")
                        .value("Email address isn't registered yet"));
    }

    @Test
    void testCreateAccount() throws Exception {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setPhoneNumber("070******41");
        profileDTO.setUsername("sammy");
        profileDTO.setEmail("test@gmail.com");
        profileDTO.setPassword("password");

        doNothing().when(userService).saveUserData(any(ProfileDTO.class));

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(profileDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("User Registration Successful"));
    }

    @Test
    void testCreateAuthenticationToken() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setUsername("test@gmail.com");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.ROLE_USER);

        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("test@gmail.com");
        request.setPassword("password");

        when(userService.loadUserByUsername(user.getUsername())).thenReturn(user);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data.id")
                        .value(1 ));
    }

    @Test
    void testCreateAuthenticationTokenWithBadCredentials() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setUsername("test@gmail.com");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.ROLE_USER);

        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("test@gmail.com");
        request.setPassword("pass");

        when(userService.loadUserByUsername(user.getUsername())).thenReturn(user);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message")
                        .value("Bad credentials" ));
    }

    @Test
    void testCreateAuthenticationTokenWithInvalidInput() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("sammy");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.error")
                        .value("UserDetailsService returned null, which is an interface contract violation" ));
    }
}
package com.example.okeyifee.service;

import com.example.okeyifee.dto.AuthRequestDTO;
import com.example.okeyifee.dto.UserDTO;
import com.example.okeyifee.exception.CustomException;
import com.example.okeyifee.models.User;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.security.JwtTokenProvider;
import com.example.okeyifee.service.impl.AuthServiceImpl;
import com.example.okeyifee.service.impl.UserServiceImpl;
import com.example.okeyifee.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.Valid;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    private AuthServiceImpl authService;

    @Autowired
    PasswordEncoder encoder;

    @MockBean
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(authenticationManager, tokenProvider,
                userService);
    }

    @Test
    void testVerifyToken() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword(encoder.encode("password"));
        String token = tokenProvider.createForgotPasswordToken(user.getEmail());

        when(userService.retrieveUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        ResponseEntity<ApiResponse> response = authService.verifyToken(token);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testVerifyTokenRejectInvalidToken() {
        when(userService.retrieveUserByEmail("test@gmail.com")).thenReturn(Optional.empty());
        String token = tokenProvider.createLoginToken("test@gmail.com");

        assertAll(
                () -> assertThrows(UsernameNotFoundException.class, () -> authService.verifyToken(token)),
                () -> assertThrows(CustomException.class, () -> authService.verifyToken("token"))
        );
    }

    @Test
    @DisplayName("Create AuthToken works on valid user")
    void testCreateAuthToken() {
        UserDTO user = new UserDTO();
        user.setUsername("test@gmail.com");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.ROLE_USER);

        @Valid UserDTO request = new UserDTO();
        request.setUsername("test@gmail.com");
        request.setPassword("password");

        when(userService.loadUserByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<ApiResponse> response = authService.login(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Create AuthToken return Unauthorized on invalid user credentials")
    void testCreateAuthTokenOnInvalid() {
        UserDTO user = new UserDTO();
        user.setUsername("test@gmail.com");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.ROLE_USER);
        when(userService.loadUserByUsername(user.getUsername())).thenReturn(user);

        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("test@gmail.com");
        request.setPassword("pass");

        ResponseEntity<ApiResponse> response = authService.login(user);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
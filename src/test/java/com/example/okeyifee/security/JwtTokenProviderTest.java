package com.example.okeyifee.security;

import com.example.okeyifee.dto.UserDTO;
import com.example.okeyifee.exception.CustomException;
import com.example.okeyifee.service.impl.UserServiceImpl;
import com.example.okeyifee.utils.Role;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
class JwtTokenProviderTest{
    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    PasswordEncoder encoder;

    @MockBean
    UserServiceImpl userService;

    @Test
    void testCreateLoginToken() {
        String token = tokenProvider.createLoginToken("test@gmail.com");
        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void testCreateForgotPasswordToken() {
        String token = tokenProvider.createForgotPasswordToken("test@gmail.com");
        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void testValidateTokenOnInvalidToken() {
        assertThrows(CustomException.class, () -> tokenProvider.validateToken("token"));
        try {
            tokenProvider.validateToken("token");
        } catch (CustomException e) {
            assertEquals("Invalid token", e.getMessage());
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        }
    }

    @Test
    void testValidateTokenOnExpiredToken() {
        String token = tokenProvider.generateToken("test@gmail.com", 0L);
        assertThrows(CustomException.class, () -> tokenProvider.validateToken(token));
        try {
            tokenProvider.validateToken(token);
        } catch (CustomException e) {
            assertEquals("Expired token", e.getMessage());
            assertEquals(HttpStatus.FORBIDDEN, e.getStatus());
        }
    }

    @Test
    void testValidateTokenOnInvalidSignature() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWN0b3JAZ21haWwuY29tIiwiaWF0IjoxNjAzMDU0NDA1LCJleHAiOjE2MDMxNDA4MDV9.ducXBAxbBBr7XhMR8FZMssM8PxNx0S29s3W9ZbJc8Us";
        assertThrows(CustomException.class, () -> tokenProvider.validateToken(token));
        try {
            tokenProvider.validateToken(token);
        } catch (CustomException e) {
            assertEquals("Invalid token", e.getMessage());
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        }
    }

    @Test
    void testValidateTokenOnEmptyToken() {
        assertThrows(CustomException.class, () -> tokenProvider.validateToken(""));
        try {
            tokenProvider.validateToken("");
        } catch (CustomException e) {
            assertEquals("Invalid token", e.getMessage());
            assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
        }
    }

    @Test
    void testGetAuthentication() {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setUsername("test@gmail.com");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.ROLE_USER);

        when(userService.loadUserByUsername(user.getUsername())).thenReturn(user);
        String token = tokenProvider.createLoginToken(user.getUsername());

        Authentication auth = tokenProvider.getAuthentication(token);

        assertNotNull(auth);
    }

    @Test
    void testGetUsername() {
        String token = tokenProvider.generateToken("test@gmail.com", 100000L);

        assertEquals("test@gmail.com", tokenProvider.getUsername(token));
    }

    @Test
    void testIsTokenExpired() {
        String token = tokenProvider.generateToken("test@gmail.com", 100000L);
        assertFalse(tokenProvider.isTokenExpired(token));
    }

    @Test
    void testIsTokenExpiredOnExpiredToken() {
        String token = tokenProvider.generateToken("test@gmail.com", 0L);

        assertThrows(ExpiredJwtException.class, () -> tokenProvider.isTokenExpired(token));
    }
}
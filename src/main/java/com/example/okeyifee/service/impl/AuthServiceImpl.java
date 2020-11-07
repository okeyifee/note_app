package com.example.okeyifee.service.impl;

//import com.decagon.kindredhair.dto.AuthRequestDTO;
//import com.decagon.kindredhair.dto.AuthResponseDTO;
//import com.decagon.kindredhair.dto.ProfileDTO;
//import com.decagon.kindredhair.dto.UserDTO;
//import com.decagon.kindredhair.models.User;
//import com.decagon.kindredhair.payload.ApiResponse;
//import com.decagon.kindredhair.security.JwtTokenProvider;
//import com.decagon.kindredhair.service.AuthService;
//import com.decagon.kindredhair.service.HairProfileService;
//import com.decagon.kindredhair.service.UserService;
import com.example.okeyifee.dto.AuthRequestDTO;
import com.example.okeyifee.dto.AuthResponseDTO;
import com.example.okeyifee.dto.ProfileDTO;
import com.example.okeyifee.dto.UserDTO;
import com.example.okeyifee.models.User;
import com.example.okeyifee.payload.ApiResponse;
import com.example.okeyifee.security.JwtTokenProvider;
import com.example.okeyifee.service.AuthService;
import com.example.okeyifee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.okeyifee.utils.BuildResponse.buildResponse;
import static org.springframework.http.HttpStatus.OK;

@Service
public class AuthServiceImpl implements AuthService{
    AuthenticationManager authenticationManager;
    JwtTokenProvider tokenProvider;
    UserService userService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider,
                           UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    public ResponseEntity<ApiResponse> verifyToken(String token) {
        // method throws custom exception if token is invalid and terminates method run
        tokenProvider.validateToken(token);
        String email = tokenProvider.getUsername(token);
        User user = userService.retrieveUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Email address isn't registered yet")
        );

        AuthResponseDTO response = new AuthResponseDTO(user.getId(), user.getEmail(), token);
        ApiResponse<AuthResponseDTO> ar = new ApiResponse<>(HttpStatus.OK);
        ar.setMessage("Valid token");
        ar.setData(response);
        return buildResponse(ar);
    }

    @Override
    public ResponseEntity<ApiResponse> createAccount(ProfileDTO profileData) {
        userService.saveUserData(profileData);

        ApiResponse<String> response = new ApiResponse<>(HttpStatus.CREATED);
        response.setMessage("User Registration Successful");
        return buildResponse(response);
    }

    @Override
    public ResponseEntity<ApiResponse> login(AuthRequestDTO authenticationRequest) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        }catch (BadCredentialsException e){
            ApiResponse<?> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED);
            response.setError(e.getMessage());
            response.setMessage("Bad credentials");
            return buildResponse(response);
        }

        final UserDTO userDetails = (UserDTO) userService.loadUserByUsername(authenticationRequest.getUsername());
        final String  token = tokenProvider.createLoginToken(userDetails.getUsername());
        AuthResponseDTO authResponse = new AuthResponseDTO(userDetails.getId(), userDetails.getUsername(), token);
        ApiResponse<AuthResponseDTO> response = new ApiResponse<>(OK);
        response.setData(authResponse);
        return buildResponse(response);
    }
}

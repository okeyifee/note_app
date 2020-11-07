package com.example.okeyifee.service;

import com.example.okeyifee.dto.ProfileDTO;
import com.example.okeyifee.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void saveUserData(ProfileDTO profileData);

    Optional<User> retrieveUserByEmail(@NotBlank String email);
}
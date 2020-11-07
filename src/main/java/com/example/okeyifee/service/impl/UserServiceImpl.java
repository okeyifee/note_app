package com.example.okeyifee.service.impl;

import com.example.okeyifee.dto.ProfileDTO;
import com.example.okeyifee.dto.UserDTO;
import com.example.okeyifee.exception.CustomException;
import com.example.okeyifee.models.User;
import com.example.okeyifee.repository.UserRepository;
import com.example.okeyifee.service.UserService;
import com.example.okeyifee.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = new UserDTO();
        User data = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + username + "not found"));
        userDTO.setId(data.getId());
        userDTO.setUsername(data.getEmail());
        userDTO.setPassword(data.getPassword());
        userDTO.setRole(data.getRole());
        return userDTO;
    }

    @Override
    public void saveUserData(ProfileDTO profileData) {
        User data = userRepository.findUserByEmail(profileData.getEmail()).orElse(null);
        if (data != null) {
            throw new CustomException("Email already exist", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User user = new User();
        user.setPhoneNumber(profileData.getPhoneNumber());
        user.setEmail(profileData.getEmail());
        user.setPassword(passwordEncoder.encode(profileData.getPassword()));
        user.setUsername(profileData.getUsername());
        user.setIsActive(true);
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    @Override
    public Optional<User> retrieveUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}

package com.example.okeyifee.service.impl;

import com.example.okeyifee.models.User;
import com.example.okeyifee.repository.UserRepository;
import com.example.okeyifee.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private PasswordEncoder bCryptPasswordEncoder;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public boolean deleteAll() {
        log.info("Deleting all restaurants ... ");
        userRepository.deleteAll();
        return true;
    }

    @Override
    public void save(User user) {
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("successfully saved user");
        } catch (Exception ioe){
            log.error("Error: " + ioe.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean findByPassword(String email, String password) {
        User user = userRepository.findUserByEmail(email).get();
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public Optional<User> findByUsername(String username){
        return Optional.ofNullable(userRepository.findByUsername(username).orElse(null));
    }
}

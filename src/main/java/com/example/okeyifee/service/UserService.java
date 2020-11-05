package com.example.okeyifee.service;

import com.example.okeyifee.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService{

    User getUser(Long id);

    User getUser(String email);

//    User createUser(User user);

    User editUser(User user);

    boolean deleteAll();

    void save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean findByPassword(String email, String password);
}

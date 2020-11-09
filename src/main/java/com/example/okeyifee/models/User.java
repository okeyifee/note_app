package com.example.okeyifee.models;

import com.example.okeyifee.utils.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static com.example.okeyifee.utils.Role.ROLE_USER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "users")
public class User extends BaseModel  {

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phoneNumber;

    @Column(nullable = true)
    private Boolean isActive;

    @Enumerated(value = EnumType.STRING)
    private Role role = ROLE_USER;

}





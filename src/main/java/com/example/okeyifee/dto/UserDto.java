package com.example.okeyifee.dto;

import lombok.*;
import lombok.Data;

@Data
@Getter
@Setter
public class UserDto{

    private Long id;

    private String username;

    private String email;

    private String phoneNumber;
}


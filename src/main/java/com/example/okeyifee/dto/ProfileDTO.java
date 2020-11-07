package com.example.okeyifee.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ProfileDTO {

    @NotBlank(message = "username should not be empty")
    private String username;

    @NotBlank(message = "phoneNumber should not be blank")
    private String phoneNumber;

    @NotBlank(message = "email should not be blank")
    private String email;

    @NotBlank(message = "password should not be blank")
    private String password;
}

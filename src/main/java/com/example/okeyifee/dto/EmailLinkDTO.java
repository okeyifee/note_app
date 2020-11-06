package com.example.okeyifee.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailLinkDTO{
    @NotBlank(message = "link cannot be blank")
    private String link;

    @NotBlank(message = "email cannot be blank")
    private String email;
}

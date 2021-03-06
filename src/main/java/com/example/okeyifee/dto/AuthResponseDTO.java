package com.example.okeyifee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private Long id;
    private String email;
    private String token;
}

package com.example.okeyifee.utils;

import com.example.okeyifee.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public class BuildResponse {

    public static <T> ResponseEntity<ApiResponse> buildResponse(ApiResponse<T> response) {
        return new ResponseEntity<>(response, response.getStatus());
    }
}

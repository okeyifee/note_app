package com.example.okeyifee.security;

import com.example.okeyifee.payload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtRequestEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestEntryPoint.class);

    ObjectMapper mapper;

    @Autowired
    public JwtRequestEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String error = e.getMessage();
        if (e.getMessage().contains("JWT expired at")){
            status = HttpStatus.FORBIDDEN;
            error = "Token has expired";
        }
        ApiResponse<?> res = new ApiResponse<>(status);
        res.setError(error);
        //set the response headers
        httpServletResponse.setStatus(status.value());
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        out.print(mapper.writeValueAsString(res));
        out.flush();
    }
}
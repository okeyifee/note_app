package com.example.okeyifee.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtRequestFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token); // this throws exception
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                throw new Exception("UNAUTHORIZED");
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
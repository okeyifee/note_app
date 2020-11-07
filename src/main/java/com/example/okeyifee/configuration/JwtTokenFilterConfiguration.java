package com.example.okeyifee.configuration;

import com.example.okeyifee.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenFilterConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtRequestFilter requestFilter;
    @Autowired
    public JwtTokenFilterConfiguration(JwtRequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        // create a new filter and add to the builder.
        builder.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
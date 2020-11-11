package com.example.okeyifee.configuration;

import com.example.okeyifee.security.JwtRequestEntryPoint;
import com.example.okeyifee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserService userDetailService;
    private JwtRequestEntryPoint unauthorizedHandler;
    private PasswordEncoder passwordEncoder;
    private JwtTokenFilterConfiguration tokenConfiguration;

    @Autowired
    public SecurityConfiguration(UserService userDetailService,
                                 JwtRequestEntryPoint unauthorizedHandler,
                                 PasswordEncoder passwordEncoder,
                                 JwtTokenFilterConfiguration tokenConfiguration) {
        this.userDetailService = userDetailService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.passwordEncoder = passwordEncoder;
        this.tokenConfiguration = tokenConfiguration;
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/auth/**")
                .antMatchers(HttpMethod.POST, "/Activate")
                .antMatchers("/v2/api-docs")
                .antMatchers("/configuration/ui")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/configuration/security")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
        http.apply(tokenConfiguration);
    }
}
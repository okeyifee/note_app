//package com.example.okeyifee.security;
//
//
//import com.example.okeyifee.models.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
//
//    private AuthenticationManager authenticationManager;
//
//    public AuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        setFilterProcessesUrl("/login");
//    }
//
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        try
//        {
//             User creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
//
//            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),new ArrayList<>()));
//        } catch(IOException e) {
//            throw new RuntimeException("Could not read request" + e);
//        }
//    }
//
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
//        String subject = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
//        String token = Jwts.builder()
//                .setSubject(subject)
//                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
//                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())
//                .compact();
//        response.addHeader("Authorization","Bearer " + token);
//        System.out.println("subject: " + subject);
//        System.out.println("token: " + token);
//    }
//}
//

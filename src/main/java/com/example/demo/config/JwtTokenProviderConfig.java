// package com.example.demo.config;

// import com.example.demo.security.JwtTokenProvider;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class JwtTokenProviderConfig {
    
//     @Value("${jwt.secret}")
//     private String secret;
    
//     @Value("${jwt.expiration}")
//     private long expiration;
    
//     @Bean
//     public JwtTokenProvider jwtTokenProvider() {
//         return new JwtTokenProvider(secret, expiration, false);
//     }
// }
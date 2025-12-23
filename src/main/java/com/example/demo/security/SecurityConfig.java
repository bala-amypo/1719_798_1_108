// package com.example.demo.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
    
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .sessionManagement(session -> 
//                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/**").permitAll()  // Allow ALL paths
//                 .anyRequest().permitAll()
//             )
//             .headers(headers -> headers
//                 .frameOptions(frame -> frame.disable())  // For H2 console
//             )
//             .formLogin(form -> form.disable())  // Disable form login
//             .httpBasic(basic -> basic.disable());  // Disable basic auth
        
//         return http.build();
//     }
// }
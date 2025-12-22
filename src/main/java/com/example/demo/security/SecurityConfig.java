package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final JwtTokenFilter jwtTokenFilter;
    
    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/hello-servlet").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider("your-secret-key", 3600000, false);
    }
}






// package com.example.demo.security;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.UserDetails;

// public class SecurityUtil {

//     public static Long getUserIdFromAuthentication(Object auth) {

//         if (auth == null) {
//             return null;
//         }

//         if (auth instanceof Long) {
//             return (Long) auth;
//         }

//         if (auth instanceof Authentication authentication) {
//             Object principal = authentication.getPrincipal();

//             if (principal instanceof Long) {
//                 return (Long) principal;
//             }

//             if (principal instanceof String) {
//                 try {
//                     return Long.parseLong((String) principal);
//                 } catch (NumberFormatException e) {
//                     return null;
//                 }
//             }

//             if (principal instanceof UserDetails) {
//                 return null;
//             }
//         }

//         return null;
//     }
// }

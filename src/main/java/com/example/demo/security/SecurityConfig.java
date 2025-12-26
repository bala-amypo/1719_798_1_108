package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/hello", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                
                // Admin only endpoints
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                // Event Manager endpoints
                .requestMatchers("/api/events/**", "/api/seat-inventory/**").hasAnyRole("ADMIN", "EVENT_MANAGER")
                
                // Pricing Analyst endpoints
                .requestMatchers("/api/pricing-rules/**", "/api/dynamic-prices/**").hasAnyRole("ADMIN", "PRICING_ANALYST", "EVENT_MANAGER")
                
                // Price adjustment logs
                .requestMatchers("/api/price-adjustments/**").hasAnyRole("ADMIN", "PRICING_ANALYST")
                
                // Allow all other API endpoints (for testing)
                .requestMatchers("/api/**").permitAll()
                
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())  // For H2 console
            )
            .formLogin(form -> form.disable())  // No login page
            .httpBasic(basic -> basic.disable());  // No basic auth
        
        return http.build();
    }
}


// // package com.example.demo.config;

// // import com.example.demo.security.JwtFilter;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.context.annotation.Bean;
// // import org.springframework.context.annotation.Configuration;
// // import org.springframework.security.authentication.AuthenticationManager;
// // import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// // import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// // import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// // import org.springframework.security.crypto.password.PasswordEncoder;
// // import org.springframework.security.web.SecurityFilterChain;
// // import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// // @Configuration
// // @EnableMethodSecurity
// // public class SecurityConfig {

// //     @Autowired
// //     private JwtFilter jwtFilter;

// //     @Bean
// //     public PasswordEncoder passwordEncoder() {
// //         return new BCryptPasswordEncoder();
// //     }

// //     @Bean
// //     public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
// //         return authConfig.getAuthenticationManager();
// //     }

// //     @Bean
// //     public SecurityFilterChain filterChain(HttpSecurity http)
// //             throws Exception {

// //         http
// //             .csrf(csrf -> csrf.disable())
// //             .authorizeHttpRequests(auth -> auth
// //                 .requestMatchers("/auth/**").permitAll()
// //                 .requestMatchers(
// //                     "/swagger-ui/**",
// //                     "/v3/api-docs/**"
// //                 ).permitAll()
// //                 .anyRequest().authenticated()
// //             )
// //             .addFilterBefore(
// //                 jwtFilter,
// //                 UsernamePasswordAuthenticationFilter.class
// //             );

// //         return http.build();
// //     }
// // }

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
//                 .requestMatchers("/**").permitAll()  
//                 .anyRequest().permitAll()
//             )
//             .headers(headers -> headers
//                 .frameOptions(frame -> frame.disable())  
//             )
//             .formLogin(form -> form.disable())  
//             .httpBasic(basic -> basic.disable()); 
        
//         return http.build();
//     }
// }
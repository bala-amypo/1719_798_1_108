package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public endpoints - NO authentication required
                .requestMatchers(
                    "/",
                    "/hello",
                    "/api/auth/**",           // Auth endpoints must be PUBLIC
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/swagger-resources/**"
                ).permitAll()  // Allow without authentication
                
                // Protected endpoints - require authentication
                .requestMatchers("/api/events/**").authenticated()
                .requestMatchers("/api/pricing-rules/**").authenticated()
                .requestMatchers("/api/seat-inventory/**").authenticated()
                .requestMatchers("/api/dynamic-prices/**").authenticated()
                
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// package com.example.demo.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final JwtAuthenticationFilter jwtAuthenticationFilter;

//     public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//         this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(AbstractHttpConfigurer::disable)
//             .sessionManagement(session -> 
//                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .authorizeHttpRequests(auth -> auth
//                 // Public endpoints
//                 .requestMatchers(
//                     "/",
//                     "/hello",
//                     "/api/auth/**",
//                     "/swagger-ui/**",
//                     "/v3/api-docs/**",
//                     "/swagger-ui.html",
//                     "/webjars/**",
//                     "/swagger-resources/**"
//                 ).permitAll()
                
//                 // Protected endpoints
//                 .requestMatchers("/api/events/**").hasAnyRole("ADMIN", "EVENT_MANAGER")
//                 .requestMatchers("/api/pricing-rules/**").hasAnyRole("ADMIN", "PRICING_ANALYST")
//                 .requestMatchers("/api/seat-inventory/**").hasAnyRole("ADMIN", "EVENT_MANAGER")
//                 .requestMatchers("/api/dynamic-prices/**").hasAnyRole("ADMIN", "PRICING_ANALYST")
//                 .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
//                 .anyRequest().authenticated()
//             )
//             .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//             .formLogin(form -> form.disable())
//             .httpBasic(basic -> basic.disable());
        
//         return http.build();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
//             throws Exception {
//         return config.getAuthenticationManager();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }

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
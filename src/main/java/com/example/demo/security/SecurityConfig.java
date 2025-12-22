// package com.example.demo.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
    
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable())
//             .authorizeHttpRequests(auth -> auth
//                 .anyRequest().permitAll()
//             );
//         return http.build();
//     }
// }





// // package com.example.demo.security;

// // import org.springframework.security.core.Authentication;
// // import org.springframework.security.core.userdetails.UserDetails;

// // public class SecurityUtil {

// //     public static Long getUserIdFromAuthentication(Object auth) {

// //         if (auth == null) {
// //             return null;
// //         }

// //         if (auth instanceof Long) {
// //             return (Long) auth;
// //         }

// //         if (auth instanceof Authentication authentication) {
// //             Object principal = authentication.getPrincipal();

// //             if (principal instanceof Long) {
// //                 return (Long) principal;
// //             }

// //             if (principal instanceof String) {
// //                 try {
// //                     return Long.parseLong((String) principal);
// //                 } catch (NumberFormatException e) {
// //                     return null;
// //                 }
// //             }

// //             if (principal instanceof UserDetails) {
// //                 return null;
// //             }
// //         }

// //         return null;
// //     }
// // }

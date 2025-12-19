package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "yourSecretKey"; // Replace with secure key
    private final long JWT_EXPIRATION = 86400000L; // 1 day in ms

    public String generateToken(Long id, String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .claim("id", id)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
}


// package com.example.demo.controller;

// import com.example.demo.dto.AuthRequest;
// import com.example.demo.dto.AuthResponse;
// import com.example.demo.model.User;
// import com.example.demo.security.JwtTokenProvider;
// import com.example.demo.service.UserService;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {

//     private final UserService userService;
//     private final JwtTokenProvider jwtTokenProvider;
//     private final PasswordEncoder passwordEncoder;

//     public AuthController(UserService userService,
//                           JwtTokenProvider jwtTokenProvider,
//                           PasswordEncoder passwordEncoder) {
//         this.userService = userService;
//         this.jwtTokenProvider = jwtTokenProvider;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @PostMapping("/register")
//     public User register(@RequestBody User user) {
//         return userService.save(user);
//     }

//     @PostMapping("/login")
//     public AuthResponse login(@RequestBody AuthRequest request) {
//         User user = userService.findByEmail(request.getEmail()).orElseThrow();
//         if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//             throw new RuntimeException("Invalid credentials");
//         }
//         String token = jwtTokenProvider.generateToken(
//                 user.getId(),
//                 user.getEmail(),
//                 user.getRole()
//         );
//         return new AuthResponse(token, user.getId(), user.getEmail(), user.getRole());
//     }
// }

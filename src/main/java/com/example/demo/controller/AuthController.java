package com.example.demo.controller;

import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                         JwtTokenProvider jwtTokenProvider,
                         CustomUserDetailsService userDetailsService,
                         BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            
            // Get user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            // Extract role from authorities
            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(auth -> auth.startsWith("ROLE_"))
                    .map(auth -> auth.substring(5))
                    .findFirst()
                    .orElse("USER");
            
            // Generate token
            String token = jwtTokenProvider.generateToken(
                authentication,
                1L, // Default userId - you might want to get actual user ID
                role
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", userDetails.getUsername());
            response.put("role", role);
            response.put("message", "Login successful");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Login failed");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user account")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Encode password
            String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
            
            // Register user
            Map<String, Object> user = userDetailsService.registerUser(
                registerRequest.getName(),
                registerRequest.getEmail(),
                encodedPassword,
                registerRequest.getRole()
            );
            
            // Generate token for newly registered user
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                registerRequest.getEmail(),
                registerRequest.getPassword()
            );
            
            String token = jwtTokenProvider.generateToken(
                authentication,
                (Long) user.get("userId"),
                (String) user.get("role")
            );
            
            user.put("token", token);
            user.put("message", "Registration successful");
            
            return ResponseEntity.ok(user);
            
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Registration failed");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(400).body(errorResponse);
        }
    }
    
    // Request DTOs
    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
        private String role = "USER";
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}

// package com.example.demo.controller;

// import com.example.demo.security.CustomUserDetailsService;
// import com.example.demo.security.JwtTokenProvider;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/auth")
// @Tag(name = "Authentication", description = "Authentication endpoints")
// public class AuthController {

//     private final AuthenticationManager authenticationManager;
//     private final JwtTokenProvider jwtTokenProvider;
//     private final CustomUserDetailsService userDetailsService;

//     public AuthController(AuthenticationManager authenticationManager,
//                          JwtTokenProvider jwtTokenProvider,
//                          CustomUserDetailsService userDetailsService) {
//         this.authenticationManager = authenticationManager;
//         this.jwtTokenProvider = jwtTokenProvider;
//         this.userDetailsService = userDetailsService;
//     }

//     @PostMapping("/login")
//     @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
//     public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
//         Authentication authentication = authenticationManager.authenticate(
//             new UsernamePasswordAuthenticationToken(
//                 loginRequest.getEmail(),
//                 loginRequest.getPassword()
//             )
//         );
        
//         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//         Map<String, Object> userData = userDetailsService.getUserData(userDetails.getUsername());
        
//         String token = jwtTokenProvider.generateToken(
//             authentication,
//             (Long) userData.get("userId"),
//             (String) userData.get("role")
//         );
        
//         Map<String, String> response = new HashMap<>();
//         response.put("token", token);
//         response.put("email", userDetails.getUsername());
//         response.put("role", (String) userData.get("role"));
        
//         return ResponseEntity.ok(response);
//     }

//     @PostMapping("/register")
//     @Operation(summary = "Register new user", description = "Register a new user account")
//     public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest registerRequest) {
//         Map<String, Object> user = userDetailsService.registerUser(
//             registerRequest.getName(),
//             registerRequest.getEmail(),
//             registerRequest.getPassword(),
//             registerRequest.getRole()
//         );
        
//         return ResponseEntity.ok(user);
//     }
    
//     // Request DTOs
//     static class LoginRequest {
//         private String email;
//         private String password;
        
//         // Getters and setters
//         public String getEmail() { return email; }
//         public void setEmail(String email) { this.email = email; }
//         public String getPassword() { return password; }
//         public void setPassword(String password) { this.password = password; }
//     }
    
//     static class RegisterRequest {
//         private String name;
//         private String email;
//         private String password;
//         private String role = "USER";
        
//         // Getters and setters
//         public String getName() { return name; }
//         public void setName(String name) { this.name = name; }
//         public String getEmail() { return email; }
//         public void setEmail(String email) { this.email = email; }
//         public String getPassword() { return password; }
//         public void setPassword(String password) { this.password = password; }
//         public String getRole() { return role; }
//         public void setRole(String role) { this.role = role; }
//     }
// }



// package com.example.demo.controller;
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.HashMap;
// import java.util.Map;
// @RestController
// @RequestMapping("/auth")
// public class AuthController {
//     @Autowired
//     private UserService userService;
//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestBody User user) {
//         if (userService.existsByEmail(user.getEmail())) {
//             Map<String, String> response = new HashMap<>();
//             response.put("error", "Email already exists");
//             return ResponseEntity.badRequest().body(response);
//         }
//         User savedUser = userService.save(user);
//         Map<String, String> response = new HashMap<>();
//         response.put("message", "User registered successfully");
//         response.put("userId", savedUser.getId().toString());
//         return ResponseEntity.ok(response);
//     }
//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//         String email = credentials.get("email");
//         String password = credentials.get("password");
        
//         User user = userService.findByEmail(email);
        
//         if (user == null || !user.getPassword().equals(password)) {
//             Map<String, String> response = new HashMap<>();
//             response.put("error", "Invalid email or password");
//             return ResponseEntity.badRequest().body(response);
//         }
//         Map<String, String> response = new HashMap<>();
//         response.put("message", "Login successful");
//         response.put("userId", user.getId().toString());
//         response.put("role", user.getRole());
//         return ResponseEntity.ok(response);
//     }
// }
// package com.example.demo.controller;

// import com.example.demo.model.User;
// import com.example.demo.security.JwtTokenProvider;
// import com.example.demo.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// import java.util.HashMap;
// import java.util.Map;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {
    
//     @Autowired
//     private UserService userService;
    
//     @Autowired
//     private PasswordEncoder passwordEncoder;
    
//     @Autowired
//     private AuthenticationManager authenticationManager;
    
//     @Autowired
//     private JwtTokenProvider jwtTokenProvider;
    
//     @PostMapping("/register")
//     public ResponseEntity<?> register(@RequestBody User user) {
//         if (userService.existsByEmail(user.getEmail())) {
//             Map<String, String> response = new HashMap<>();
//             response.put("error", "Email already exists");
//             return ResponseEntity.badRequest().body(response);
//         }
        
//         // Encode password before saving
//         user.setPassword(passwordEncoder.encode(user.getPassword()));
        
//         // Set default role if not provided
//         if (user.getRole() == null || user.getRole().isEmpty()) {
//             user.setRole("USER");
//         }
        
//         User savedUser = userService.save(user);
        
//         Map<String, Object> response = new HashMap<>();
//         response.put("message", "User registered successfully");
//         response.put("userId", savedUser.getId());
//         response.put("email", savedUser.getEmail());
//         response.put("role", savedUser.getRole());
        
//         return ResponseEntity.ok(response);
//     }
    
//     @PostMapping("/login")
//     public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//         String email = credentials.get("email");
//         String password = credentials.get("password");
        
//         try {
//             Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(email, password)
//             );
            
//             SecurityContextHolder.getContext().setAuthentication(authentication);
            
//             User user = userService.findByEmail(email);
            
//             // Generate JWT token
//             String token = jwtTokenProvider.generateToken(authentication, user.getId(), user.getRole());
            
//             Map<String, Object> response = new HashMap<>();
//             response.put("message", "Login successful");
//             response.put("token", token);
//             response.put("userId", user.getId());
//             response.put("email", user.getEmail());
//             response.put("role", user.getRole());
//             response.put("fullName", user.getFullName());
            
//             return ResponseEntity.ok(response);
            
//         } catch (Exception e) {
//             Map<String, String> response = new HashMap<>();
//             response.put("error", "Invalid email or password");
//             return ResponseEntity.badRequest().body(response);
//         }
//     }
    
//     @GetMapping("/profile")
//     public ResponseEntity<?> getProfile() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         String email = authentication.getName();
        
//         User user = userService.findByEmail(email);
//         if (user == null) {
//             return ResponseEntity.notFound().build();
//         }
        
//         Map<String, Object> response = new HashMap<>();
//         response.put("userId", user.getId());
//         response.put("email", user.getEmail());
//         response.put("fullName", user.getFullName());
//         response.put("role", user.getRole());
//         response.put("createdAt", user.getCreatedAt());
        
//         return ResponseEntity.ok(response);
//     }
    
//     @GetMapping("/validate-token")
//     public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             return ResponseEntity.badRequest().body(Map.of("valid", false, "error", "Invalid token format"));
//         }
        
//         String token = authHeader.substring(7);
//         boolean isValid = jwtTokenProvider.validateToken(token);
        
//         Map<String, Object> response = new HashMap<>();
//         response.put("valid", isValid);
        
//         if (isValid) {
//             Map<String, Object> claims = jwtTokenProvider.getAllClaims(token);
//             response.put("claims", claims);
//         }
        
//         return ResponseEntity.ok(response);
//     }
// }

package com.example.demo.controller;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }
        User savedUser = userService.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("userId", savedUser.getId().toString());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        User user = userService.findByEmail(email);
        
        if (user == null || !user.getPassword().equals(password)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid email or password");
            return ResponseEntity.badRequest().body(response);
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("userId", user.getId().toString());
        response.put("role", user.getRole());
        return ResponseEntity.ok(response);
    }
}
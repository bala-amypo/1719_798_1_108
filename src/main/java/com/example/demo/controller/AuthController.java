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
package com.example.demo.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final Map<String, Map<String, Object>> users = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public CustomUserDetailsService() {
        // Initialize with some default users
        registerUser("Admin User", "admin@example.com", 
                    encoder.encode("admin123"), "ADMIN");
        registerUser("Event Manager", "manager@example.com", 
                    encoder.encode("manager123"), "EVENT_MANAGER");
        registerUser("Pricing Analyst", "analyst@example.com", 
                    encoder.encode("analyst123"), "PRICING_ANALYST");
    }
    
    public Map<String, Object> registerUser(String name, String email, 
                                           String encodedPassword, String role) {
        Long userId = idCounter.getAndIncrement();
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("name", name);
        userData.put("email", email);
        userData.put("password", encodedPassword);
        userData.put("role", role);
        
        users.put(email, userData);
        return userData;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> userData = users.get(username);
        
        if (userData == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + userData.get("role"))
        );
        
        return new User(
                username,
                (String) userData.get("password"),
                authorities
        );
    }
}











// package com.example.demo.security;

// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {
    
//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
    
//     public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }
    
//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         User user = userRepository.findByEmail(email)
//             .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
//         return org.springframework.security.core.userdetails.User.builder()
//             .username(user.getEmail())
//             .password(user.getPassword())
//             .roles(user.getRole())
//             .build();
//     }
    
//     public User registerUser(String fullName, String email, String password, String role) {
//         if (userRepository.existsByEmail(email)) {
//             throw new RuntimeException("Email already exists");
//         }
        
//         User user = new User();
//         user.setFullName(fullName);
//         user.setEmail(email);
//         user.setPassword(passwordEncoder.encode(password));
//         user.setRole(role);
        
//         return userRepository.save(user);
//     }
// }
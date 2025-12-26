package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final Map<String, Map<String, Object>> users = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Autowired
    private UserRepository userRepository;
    
    public CustomUserDetailsService() {
        // Initialize with test users
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
        // First try to get from in-memory map (for tests)
        Map<String, Object> userData = users.get(username);
        
        if (userData != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + userData.get("role"))
            );
            
            return new User(
                    username,
                    (String) userData.get("password"),
                    authorities
            );
        }
        
        // If not found in memory, try database
        User dbUser = userRepository.findByEmail(username);
        if (dbUser != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + dbUser.getRole())
            );
            
            return new User(
                    dbUser.getEmail(),
                    dbUser.getPassword(),
                    authorities
            );
        }
        
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}


// package com.example.demo.security;

// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.*;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.atomic.AtomicLong;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {
    
//     private final Map<String, Map<String, Object>> users = new ConcurrentHashMap<>();
//     private final AtomicLong idCounter = new AtomicLong(1);
//     private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
//     public CustomUserDetailsService() {
        
//         registerUser("Admin User", "admin@example.com", 
//                     encoder.encode("admin123"), "ADMIN");
//         registerUser("Event Manager", "manager@example.com", 
//                     encoder.encode("manager123"), "EVENT_MANAGER");
//         registerUser("Pricing Analyst", "analyst@example.com", 
//                     encoder.encode("analyst123"), "PRICING_ANALYST");
//     }
    
//     public Map<String, Object> registerUser(String name, String email, 
//                                            String encodedPassword, String role) {
//         Long userId = idCounter.getAndIncrement();
//         Map<String, Object> userData = new HashMap<>();
//         userData.put("userId", userId);
//         userData.put("name", name);
//         userData.put("email", email);
//         userData.put("password", encodedPassword);
//         userData.put("role", role);
        
//         users.put(email, userData);
//         return userData;
//     }
    
//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         Map<String, Object> userData = users.get(username);
        
//         if (userData == null) {
//             throw new UsernameNotFoundException("User not found with username: " + username);
//         }
        
//         List<SimpleGrantedAuthority> authorities = Collections.singletonList(
//                 new SimpleGrantedAuthority("ROLE_" + userData.get("role"))
//         );
        
//         return new User(
//                 username,
//                 (String) userData.get("password"),
//                 authorities
//         );
//     }
// }
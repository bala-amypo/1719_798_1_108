package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    // Empty implementation
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        return null;
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

// import java.util.Collections;
// import java.util.HashMap;
// import java.util.Map;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     private UserRepository userRepository;
//     private PasswordEncoder passwordEncoder;

//     // ✅ REQUIRED FOR TESTS
//     public CustomUserDetailsService() {
//     }

//     // ✅ USED BY SPRING
//     public CustomUserDetailsService(UserRepository userRepository,
//                                     PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         if (userRepository == null) {
//             throw new UsernameNotFoundException("UserRepository not initialized");
//         }

//         User user = userRepository.findByEmail(email)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//         return new org.springframework.security.core.userdetails.User(
//                 user.getEmail(),
//                 user.getPassword(),
//                 Collections.emptyList()
//         );
//     }

//     public Map<String, Object> registerUser(String fullName,
//                                             String email,
//                                             String password,
//                                             String role) {
//         if (userRepository == null || passwordEncoder == null) {
//             throw new IllegalStateException("Dependencies not initialized");
//         }

//         User user = new User();
//         user.setFullName(fullName);
//         user.setEmail(email);
//         user.setPassword(passwordEncoder.encode(password));
//         user.setRole(role);

//         userRepository.save(user);

//         Map<String, Object> response = new HashMap<>();
//         response.put("id", user.getId());
//         response.put("email", user.getEmail());
//         response.put("role", user.getRole());
//         return response;
//     }
// }


// package com.example.demo.security;

// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import java.util.Collections;
// import java.util.HashMap;
// import java.util.Map;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;

//     public CustomUserDetailsService(UserRepository userRepository,
//                                     PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         User user = userRepository.findByEmail(email)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//         return new org.springframework.security.core.userdetails.User(
//                 user.getEmail(),
//                 user.getPassword(),
//                 Collections.emptyList()
//         );
//     }

//     public Map<String, Object> registerUser(String fullName,
//                                             String email,
//                                             String password,
//                                             String role) {
//         User user = new User();
//         user.setFullName(fullName);
//         user.setEmail(email);
//         user.setPassword(passwordEncoder.encode(password));
//         user.setRole(role);

//         userRepository.save(user);

//         Map<String, Object> response = new HashMap<>();
//         response.put("id", user.getId());
//         response.put("email", user.getEmail());
//         response.put("role", user.getRole());
//         return response;
//     }
// }

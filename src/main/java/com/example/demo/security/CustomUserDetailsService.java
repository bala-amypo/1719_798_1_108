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
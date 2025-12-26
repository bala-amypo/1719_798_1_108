// package com.example.demo.service.impl;

// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class UserImpl implements UserService {
    
//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
    
//     @Autowired
//     public UserImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }
    
//     @Override
//     public User save(User user) {
//         // Ensure password is encoded
//         if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
//             user.setPassword(passwordEncoder.encode(user.getPassword()));
//         }
//         return userRepository.save(user);
//     }
    
//     @Override
//     public User findByEmail(String email) {
//         return userRepository.findByEmail(email);
//     }
    
//     @Override
//     public boolean existsByEmail(String email) {
//         return userRepository.existsByEmail(email);
//     }
// }

package com.example.demo.service.impl;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
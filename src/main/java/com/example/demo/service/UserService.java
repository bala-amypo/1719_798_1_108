package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public User save(User user) {
        // Check for duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}


// package com.example.demo.service;

// import com.example.demo.model.User;

// import java.util.Optional;

// public interface UserService {

//     User save(User user);

//     Optional<User> findByEmail(String email);

//     boolean existsByEmail(String email);
// }

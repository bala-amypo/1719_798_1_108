package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User save(User user);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}

// package com.example.demo.service;

// import com.example.demo.model.User;

// import java.util.Optional;

// public interface UserService {

//     User save(User user);

//     Optional<User> findByEmail(String email);

//     boolean existsByEmail(String email);
// }

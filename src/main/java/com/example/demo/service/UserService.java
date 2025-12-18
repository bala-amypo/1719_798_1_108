package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User save(User user);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}

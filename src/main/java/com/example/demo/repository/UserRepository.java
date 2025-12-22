package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}






// package com.example.demo.repository;

// import com.example.demo.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.Optional;

// public interface UserRepository extends JpaRepository<User, Long> {

//     Optional<User> findByEmail(String email);
// }

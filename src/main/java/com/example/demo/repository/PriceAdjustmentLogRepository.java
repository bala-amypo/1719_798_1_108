package com.example.demo.repository;

import com.example.demo.model.PriceAdjustmentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriceAdjustmentLogRepository extends JpaRepository<PriceAdjustmentLog, Long> {
    List<PriceAdjustmentLog> findByEventId(Long eventId);
}
package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}


// package com.example.demo.repository;

// import com.example.demo.model.PriceAdjustmentLog;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

// public interface PriceAdjustmentLogRepository extends JpaRepository<PriceAdjustmentLog, Long> {

//     List<PriceAdjustmentLog> findByEventId(Long eventId);
// }

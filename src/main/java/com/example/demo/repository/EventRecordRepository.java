package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.EventRecord;
import java.util.Optional;

public interface EventRecordRepository extends JpaRepository<EventRecord, Long> {
    boolean existsByEventCode(String code);
    Optional<EventRecord> findByEventCode(String code);
}

package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.example.demo.entity.SeatInventoryRecord;

public interface SeatInventoryRecordRepository extends JpaRepository<SeatInventoryRecord, Long> {
    Optional<SeatInventoryRecord> findByEventId(Long eventId);
}
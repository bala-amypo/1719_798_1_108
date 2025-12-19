package com.example.demo.repository;

import com.example.demo.model.SeatInventoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventoryRecord, Long> {

    Optional<SeatInventoryRecord> findByEventId(Long eventId);

    List<SeatInventoryRecord> findAll();
}

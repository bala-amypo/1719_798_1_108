package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SeatInventoryRecord;

public interface SeatInventoryRecordRepository
        extends JpaRepository<SeatInventoryRecord, Long> {

    Optional<SeatInventoryRecord> findByEventId(Long eventId);
}

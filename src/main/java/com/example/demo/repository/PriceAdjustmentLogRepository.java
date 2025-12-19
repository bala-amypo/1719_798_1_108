package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PriceAdjustmentLog;

public interface PriceAdjustmentLogRepository
        extends JpaRepository<PriceAdjustmentLog, Long> {

    List<PriceAdjustmentLog> findByEventId(Long eventId);
}

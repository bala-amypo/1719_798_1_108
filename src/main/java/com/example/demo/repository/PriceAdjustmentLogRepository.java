package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.example.demo.entity.PriceAdjustmentLog;
public interface PriceAdjustmentLogRepository extends JpaRepository<PriceAdjustmentLog, Long> {
    List<PriceAdjustmentLog> findByEventId(Long eventId);
}
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.example.demo.entity.DynamicPriceRecord;



public interface DynamicPriceRecordRepository extends JpaRepository<DynamicPriceRecord, Long> {
    List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId);
    DynamicPriceRecord findFirstByEventIdOrderByComputedAtDesc(Long eventId);
}


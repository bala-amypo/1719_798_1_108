package com.example.demo.service;

import com.example.demo.model.DynamicPriceRecord;
import java.util.List;
import java.util.Optional;

public interface DynamicPriceRecordService {
    DynamicPriceRecord save(DynamicPriceRecord record);
    Optional<DynamicPriceRecord> findLatestByEventId(Long eventId);
    List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId);
    List<DynamicPriceRecord> findAll();
}
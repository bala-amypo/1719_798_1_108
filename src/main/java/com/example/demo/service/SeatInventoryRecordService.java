package com.example.demo.service;
import com.example.demo.model.SeatInventoryRecord;
public interface SeatInventoryRecordService {
    SeatInventoryRecord save(SeatInventoryRecord record);
    SeatInventoryRecord findByEventId(Long eventId);
}
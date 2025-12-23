package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;

public interface SeatInventoryService {
    SeatInventoryRecord createInventory(SeatInventoryRecord inventory);
    SeatInventoryRecord getInventoryByEvent(Long eventId);
}
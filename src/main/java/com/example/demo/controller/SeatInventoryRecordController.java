package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat-inventory")
public class SeatInventoryRecordController {
    
    private final SeatInventoryService seatInventoryService;
    
    public SeatInventoryRecordController(SeatInventoryService seatInventoryService) {
        this.seatInventoryService = seatInventoryService;
    }
    
    @PostMapping
    public ResponseEntity<SeatInventoryRecord> createInventory(@RequestBody SeatInventoryRecord inventory) {
        SeatInventoryRecord saved = seatInventoryService.createInventory(inventory);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping("/events/{eventId}")
    public ResponseEntity<SeatInventoryRecord> getInventoryByEvent(@PathVariable Long eventId) {
        SeatInventoryRecord inventory = seatInventoryService.getInventoryByEvent(eventId);
        return ResponseEntity.ok(inventory);
    }
}
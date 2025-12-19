package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class SeatInventoryController {

    private final SeatInventoryService inventoryService;

    public SeatInventoryController(SeatInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public SeatInventoryRecord create(@RequestBody SeatInventoryRecord inventory) {
        return inventoryService.createInventory(inventory);
    }

    @PutMapping("/{eventId}/remaining")
    public SeatInventoryRecord updateRemaining(
            @PathVariable Long eventId,
            @RequestParam Integer remainingSeats) {
        return inventoryService.updateRemainingSeats(eventId, remainingSeats);
    }

    @GetMapping("/event/{eventId}")
    public SeatInventoryRecord getByEvent(@PathVariable Long eventId) {
        return inventoryService.getInventoryByEvent(eventId).orElseThrow();
    }

    @GetMapping
    public List<SeatInventoryRecord> getAll() {
        return inventoryService.getAllInventories();
    }
}

package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.impl.SeatInventoryServiceimpl;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Seat Inventory")
public class SeatInventoryController {

    private final SeatInventoryServiceImpl service;

    public SeatInventoryController(SeatInventoryServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public SeatInventoryRecord create(@RequestBody SeatInventoryRecord inventory) {
        return service.createInventory(inventory);
    }

    @PutMapping("/{eventId}/remaining")
    public SeatInventoryRecord updateRemaining(
            @PathVariable Long eventId,
            @RequestParam Integer remainingSeats) {
        return service.updateRemainingSeats(eventId, remainingSeats);
    }

    @GetMapping("/event/{eventId}")
    public SeatInventoryRecord getByEvent(@PathVariable Long eventId) {
        return service.getInventoryByEvent(eventId);
    }

    @GetMapping
    public List<SeatInventoryRecord> getAll() {
        return service.getAllInventories();
    }
}

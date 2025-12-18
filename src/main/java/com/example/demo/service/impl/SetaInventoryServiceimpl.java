package com.example.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import com.example.demo.exception.BadRequestException;

@Service
public class SeatInventoryServiceImpl {

    private final SeatInventoryRecordRepository invRepo;
    private final EventRecordRepository eventRepo;

    public SeatInventoryServiceImpl(SeatInventoryRecordRepository invRepo, EventRecordRepository eventRepo) {
        this.invRepo = invRepo;
        this.eventRepo = eventRepo;
    }

    public SeatInventoryRecord createInventory(SeatInventoryRecord inv) {
        if (inv.getRemainingSeats() > inv.getTotalSeats())
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        eventRepo.findById(inv.getEventId()).orElseThrow();
        return invRepo.save(inv);
    }

    public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remaining) {
        SeatInventoryRecord inv = invRepo.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));
        if (remaining > inv.getTotalSeats())
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        inv.setRemainingSeats(remaining);
        return invRepo.save(inv);
    }

    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return invRepo.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));
    }

    public List<SeatInventoryRecord> getAllInventories() {
        return invRepo.findAll();
    }
}

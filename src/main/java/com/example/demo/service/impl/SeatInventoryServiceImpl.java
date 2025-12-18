package com.example.demo.service.impl;

import com.example.demo.entity.SeatInventoryRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRecordRepository inventoryRepository;
    private final EventRecordRepository eventRepository;

    public SeatInventoryServiceImpl(SeatInventoryRecordRepository inventoryRepository,
                                    EventRecordRepository eventRepository) {
        this.inventoryRepository = inventoryRepository;
        this.eventRepository = eventRepository;
    }

    public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
        if (inventory.getRemainingSeats() > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        if (!eventRepository.existsById(inventory.getEventId())) {
            throw new BadRequestException("Seat inventory not found");
        }
        return inventoryRepository.save(inventory);
    }

    public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
        SeatInventoryRecord inventory = getInventoryByEvent(eventId);
        if (remainingSeats > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        inventory.setRemainingSeats(remainingSeats);
        return inventoryRepository.save(inventory);
    }

    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));
    }

    public List<SeatInventoryRecord> getAllInventories() {
        return inventoryRepository.findAll();
    }
}

package com.example.demo.service.impl;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.EventRecord;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {
    private final SeatInventoryRecordRepository seatInventoryRecordRepository;
    private final EventRecordRepository eventRecordRepository;
    public SeatInventoryServiceImpl(SeatInventoryRecordRepository seatInventoryRecordRepository,EventRecordRepository eventRecordRepository) {
        this.seatInventoryRecordRepository = seatInventoryRecordRepository;
        this.eventRecordRepository = eventRecordRepository;
    }
    @Override
    @Transactional
    public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
        EventRecord event = eventRecordRepository.findById(inventory.getEventId()).orElseThrow(() -> new NotFoundException("Event not found with id: " + inventory.getEventId()));
                if (inventory.getTotalSeats() == null || inventory.getTotalSeats() <= 0) {
            throw new BadRequestException("Total seats must be > 0");
        }
        if (inventory.getRemainingSeats() == null) {
            inventory.setRemainingSeats(inventory.getTotalSeats());
        }
        if (inventory.getRemainingSeats() > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        if (inventory.getRemainingSeats() < 0) {
            throw new BadRequestException("Remaining seats cannot be negative");
        }
        return seatInventoryRecordRepository.save(inventory);
    }
    @Override
    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return seatInventoryRecordRepository.findByEventId(eventId)
                .orElseThrow(() -> new NotFoundException("Seat inventory not found for event id: " + eventId));
    }
}
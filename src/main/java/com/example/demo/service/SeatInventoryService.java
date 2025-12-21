package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EventRecord;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SeatInventoryService {
    
    private final SeatInventoryRecordRepository seatInventoryRecordRepository;
    private final EventRecordRepository eventRecordRepository;
    
    public SeatInventoryService(
            SeatInventoryRecordRepository seatInventoryRecordRepository,
            EventRecordRepository eventRecordRepository) {
        this.seatInventoryRecordRepository = seatInventoryRecordRepository;
        this.eventRecordRepository = eventRecordRepository;
    }
    
    @Transactional
    public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
        // Validate event exists
        EventRecord event = eventRecordRepository.findById(inventory.getEventId())
            .orElseThrow(() -> new BadRequestException("Event not found"));
        
        // Validate seats
        if (inventory.getRemainingSeats() > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        
        // Check if inventory already exists for this event
        Optional<SeatInventoryRecord> existing = seatInventoryRecordRepository
            .findByEventId(inventory.getEventId());
        if (existing.isPresent()) {
            throw new BadRequestException("Inventory already exists for this event");
        }
        
        return seatInventoryRecordRepository.save(inventory);
    }
    
    @Transactional
    public void updateRemainingSeats(Long eventId, Integer remainingSeats) {
        SeatInventoryRecord inventory = seatInventoryRecordRepository
            .findByEventId(eventId)
            .orElseThrow(() -> new BadRequestException("Seat inventory not found"));
        
        if (remainingSeats > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        
        inventory.setRemainingSeats(remainingSeats);
        seatInventoryRecordRepository.save(inventory);
    }
    
    public Optional<SeatInventoryRecord> getInventoryByEvent(Long eventId) {
        return seatInventoryRecordRepository.findByEventId(eventId);
    }
    
    public List<SeatInventoryRecord> getAllInventories() {
        return seatInventoryRecordRepository.findAll();
    }
}















// package com.example.demo.service;

// import com.example.demo.model.SeatInventoryRecord;

// import java.util.List;

// public interface SeatInventoryService {

//     SeatInventoryRecord createInventory(SeatInventoryRecord record);

//     SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats);

//     SeatInventoryRecord getInventoryByEvent(Long eventId);

//     List<SeatInventoryRecord> getAllInventories();
// }

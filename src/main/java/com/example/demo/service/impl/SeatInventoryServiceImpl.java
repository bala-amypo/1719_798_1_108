package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EventRecord;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {
    
    private final SeatInventoryRecordRepository seatInventoryRecordRepository;
    private final EventRecordRepository eventRecordRepository;
    
    public SeatInventoryServiceImpl(SeatInventoryRecordRepository seatInventoryRecordRepository,
                                   EventRecordRepository eventRecordRepository) {
        this.seatInventoryRecordRepository = seatInventoryRecordRepository;
        this.eventRecordRepository = eventRecordRepository;
    }
    
    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
        if (inventory.getRemainingSeats() > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        
        if (!eventRecordRepository.existsById(inventory.getEventId())) {
            throw new BadRequestException("Event not found");
        }
        
        if (seatInventoryRecordRepository.findByEventId(inventory.getEventId()).isPresent()) {
            throw new BadRequestException("Inventory already exists for this event");
        }
        
        return seatInventoryRecordRepository.save(inventory);
    }
    
    @Override
    public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
        SeatInventoryRecord inventory = seatInventoryRecordRepository.findByEventId(eventId)
            .orElseThrow(() -> new BadRequestException("Seat inventory not found"));
        
        if (remainingSeats > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        
        inventory.setRemainingSeats(remainingSeats);
        return seatInventoryRecordRepository.save(inventory);
    }
    
    @Override
    public Optional<SeatInventoryRecord> getInventoryByEvent(Long eventId) {
        return seatInventoryRecordRepository.findByEventId(eventId);
    }
    
    @Override
    public List<SeatInventoryRecord> getAllInventories() {
        return seatInventoryRecordRepository.findAll();
    }
}

















// package com.example.demo.service.impl;

// import com.example.demo.exception.BadRequestException;
// import com.example.demo.model.SeatInventoryRecord;
// import com.example.demo.model.EventRecord;
// import com.example.demo.repository.SeatInventoryRecordRepository;
// import com.example.demo.repository.EventRecordRepository;
// import com.example.demo.service.SeatInventoryService;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class SeatInventoryServiceImpl implements SeatInventoryService {

//     private final SeatInventoryRecordRepository inventoryRepository;
//     private final EventRecordRepository eventRepository;

//     public SeatInventoryServiceImpl(
//             SeatInventoryRecordRepository inventoryRepository,
//             EventRecordRepository eventRepository) {
//         this.inventoryRepository = inventoryRepository;
//         this.eventRepository = eventRepository;
//     }

//     @Override
//     public SeatInventoryRecord createInventory(SeatInventoryRecord record) {
//         return inventoryRepository.save(record);
//     }

//     @Override
//     public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
//         SeatInventoryRecord inventory = inventoryRepository.findByEvent_Id(eventId)
//                 .orElseThrow(() -> new BadRequestException("Inventory not found"));

//         inventory.setRemainingSeats(remainingSeats);
//         return inventoryRepository.save(inventory);
//     }

//     @Override
//     public SeatInventoryRecord getInventoryByEvent(Long eventId) {
//         return inventoryRepository.findByEvent_Id(eventId)
//                 .orElseThrow(() -> new BadRequestException("Inventory not found"));
//     }

//     @Override
//     public List<SeatInventoryRecord> getAllInventories() {
//         return inventoryRepository.findAll();
//     }
// }

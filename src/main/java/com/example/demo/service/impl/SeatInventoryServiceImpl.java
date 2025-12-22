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
    
    public SeatInventoryServiceImpl(SeatInventoryRecordRepository seatInventoryRecordRepository,
                                   EventRecordRepository eventRecordRepository) {
        this.seatInventoryRecordRepository = seatInventoryRecordRepository;
        this.eventRecordRepository = eventRecordRepository;
    }
    
    @Override
    @Transactional
    public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
        // Check if event exists
        EventRecord event = eventRecordRepository.findById(inventory.getEventId())
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + inventory.getEventId()));
        
        // Validate inventory
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












// package com.example.demo.service.impl;

// import com.example.demo.model.EventRecord;
// import com.example.demo.model.SeatInventoryRecord;
// import com.example.demo.repository.EventRecordRepository;
// import com.example.demo.repository.SeatInventoryRecordRepository;
// import com.example.demo.service.SeatInventoryRecordService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class SeatInventoryRecordImpl implements SeatInventoryRecordService {
    
//     private final SeatInventoryRecordRepository inventoryRepository;
//     private final EventRecordRepository eventRecordRepository;
    
//     @Autowired
//     public SeatInventoryRecordImpl(SeatInventoryRecordRepository inventoryRepository,
//                                   EventRecordRepository eventRecordRepository) {
//         this.inventoryRepository = inventoryRepository;
//         this.eventRecordRepository = eventRecordRepository;
//     }
    
//     @Override
//     public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
//         // Validate that event exists
//         EventRecord event = eventRecordRepository.findById(inventory.getEventId())
//                 .orElseThrow(() -> new RuntimeException("Event not found"));
        
//         // Validate seats
//         if (inventory.getRemainingSeats() > inventory.getTotalSeats()) {
//             throw new RuntimeException("Remaining seats cannot exceed total seats");
//         }
        
//         return inventoryRepository.save(inventory);
//     }
    
//     @Override
//     public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
//         Optional<SeatInventoryRecord> inventoryOpt = inventoryRepository.findByEventId(eventId);
        
//         if (!inventoryOpt.isPresent()) {
//             throw new RuntimeException("Seat inventory not found");
//         }
        
//         SeatInventoryRecord inventory = inventoryOpt.get();
        
//         // Validate seats
//         if (remainingSeats > inventory.getTotalSeats()) {
//             throw new RuntimeException("Remaining seats cannot exceed total seats");
//         }
        
//         inventory.setRemainingSeats(remainingSeats);
//         return inventoryRepository.save(inventory);
//     }
    
//     @Override
//     public Optional<SeatInventoryRecord> getInventoryByEvent(Long eventId) {
//         return inventoryRepository.findByEventId(eventId);
//     }
    
//     @Override
//     public List<SeatInventoryRecord> getAllInventories() {
//         return inventoryRepository.findAll();
//     }
// }

















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

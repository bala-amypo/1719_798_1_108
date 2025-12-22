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












// package com.example.demo.controller;

// import com.example.demo.model.SeatInventoryRecord;
// import com.example.demo.service.SeatInventoryService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/inventory")
// public class SeatInventoryController {

//     private final SeatInventoryService inventoryService;

//     public SeatInventoryController(SeatInventoryService inventoryService) {
//         this.inventoryService = inventoryService;
//     }

//     @PostMapping
//     public SeatInventoryRecord create(@RequestBody SeatInventoryRecord record) {
//         return inventoryService.createInventory(record);
//     }

//     @PutMapping("/{eventId}/{remainingSeats}")
//     public SeatInventoryRecord update(@PathVariable Long eventId,
//                                       @PathVariable Integer remainingSeats) {
//         return inventoryService.updateRemainingSeats(eventId, remainingSeats);
//     }

//     @GetMapping("/{eventId}")
//     public SeatInventoryRecord getByEvent(@PathVariable Long eventId) {
//         return inventoryService.getInventoryByEvent(eventId);
//     }

//     @GetMapping
//     public List<SeatInventoryRecord> getAll() {
//         return inventoryService.getAllInventories();
//     }
// }

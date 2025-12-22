package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class SeatInventoryRecordController {
    
    @Autowired
    private SeatInventoryRecordService inventoryService;
    
    @PostMapping
    public ResponseEntity<SeatInventoryRecord> createInventory(@RequestBody SeatInventoryRecord inventory) {
        SeatInventoryRecord createdInventory = inventoryService.createInventory(inventory);
        return ResponseEntity.ok(createdInventory);
    }
    
    @PutMapping("/{eventId}/remaining")
    public ResponseEntity<SeatInventoryRecord> updateRemainingSeats(@PathVariable Long eventId,
                                                                   @RequestParam Integer remainingSeats) {
        SeatInventoryRecord updatedInventory = inventoryService.updateRemainingSeats(eventId, remainingSeats);
        return ResponseEntity.ok(updatedInventory);
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<SeatInventoryRecord> getInventoryByEvent(@PathVariable Long eventId) {
        Optional<SeatInventoryRecord> inventory = inventoryService.getInventoryByEvent(eventId);
        return inventory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<SeatInventoryRecord>> getAllInventories() {
        List<SeatInventoryRecord> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
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

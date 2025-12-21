package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Seat Inventory Management", description = "Endpoints for managing seat inventories")
public class SeatInventoryController {
    
    private final SeatInventoryService seatInventoryService;
    
    public SeatInventoryController(SeatInventoryService seatInventoryService) {
        this.seatInventoryService = seatInventoryService;
    }
    
    @PostMapping
    public ResponseEntity<SeatInventoryRecord> createInventory(
            @RequestBody SeatInventoryRecord inventory) {
        SeatInventoryRecord created = seatInventoryService.createInventory(inventory);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{eventId}/remaining")
    public ResponseEntity<Void> updateRemainingSeats(
            @PathVariable Long eventId,
            @RequestParam Integer remainingSeats) {
        seatInventoryService.updateRemainingSeats(eventId, remainingSeats);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<SeatInventoryRecord> getByEvent(@PathVariable Long eventId) {
        return seatInventoryService.getInventoryByEvent(eventId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<SeatInventoryRecord>> getAllInventories() {
        List<SeatInventoryRecord> inventories = seatInventoryService.getAllInventories();
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

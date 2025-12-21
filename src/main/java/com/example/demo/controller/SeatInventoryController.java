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

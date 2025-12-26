package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Event management endpoints")
@SecurityRequirement(name = "bearerAuth") 
public class EventRecordController {
    
    private final EventRecordService eventRecordService;
    
    public EventRecordController(EventRecordService eventRecordService) {
        this.eventRecordService = eventRecordService;
    }
    
    @PostMapping
    @Operation(summary = "Create event", description = "Create a new event")
    public ResponseEntity<EventRecord> createEvent(@RequestBody EventRecord event) {
        EventRecord created = eventRecordService.createEvent(event);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping
    @Operation(summary = "Get all events", description = "Retrieve all events")
    public ResponseEntity<List<EventRecord>> getAllEvents() {
        List<EventRecord> events = eventRecordService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID", description = "Retrieve event by its ID")
    public ResponseEntity<EventRecord> getEventById(@PathVariable Long id) {
        EventRecord event = eventRecordService.getEventById(id);
        return ResponseEntity.ok(event);
    }
}

// package com.example.demo.controller;
// import com.example.demo.model.EventRecord;
// import com.example.demo.service.EventRecordService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.List;
// import java.util.Optional;
// @RestController
// @RequestMapping("/api/events")
// public class EventRecordController {
//     private final EventRecordService eventRecordService;
//     public EventRecordController(EventRecordService eventRecordService) {
//         this.eventRecordService = eventRecordService;
//     }
//     @PostMapping
//     public ResponseEntity<EventRecord> createEvent(@RequestBody EventRecord event) {
//         EventRecord created = eventRecordService.createEvent(event);
//         return ResponseEntity.ok(created);
//     }
//     @GetMapping
//     public ResponseEntity<List<EventRecord>> getAllEvents() {
//         List<EventRecord> events = eventRecordService.getAllEvents();
//         return ResponseEntity.ok(events);
//     }
//     @GetMapping("/{id}")
//     public ResponseEntity<EventRecord> getEventById(@PathVariable Long id) {
//         EventRecord event = eventRecordService.getEventById(id);
//         return ResponseEntity.ok(event);
//     }
//     @GetMapping("/code/{code}")
//     public ResponseEntity<EventRecord> getEventByCode(@PathVariable String code) {
//         Optional<EventRecord> event = eventRecordService.getEventByCode(code);
//         return event.map(ResponseEntity::ok)
//                    .orElse(ResponseEntity.notFound().build());
//     }
//     @PutMapping("/{id}/status")
//     public ResponseEntity<EventRecord> updateEventStatus(
//             @PathVariable Long id, 
//             @RequestParam Boolean active) {
//         EventRecord updated = eventRecordService.updateEventStatus(id, active);
//         return ResponseEntity.ok(updated);
//     }
// }
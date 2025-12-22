package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Management", description = "APIs for managing events")
public class EventRecordController {
    
    private final EventRecordService eventRecordService;
    
    public EventRecordController(EventRecordService eventRecordService) {
        this.eventRecordService = eventRecordService;
    }
    
    @PostMapping
    public ResponseEntity<EventRecord> createEvent(@RequestBody EventRecord event) {
        EventRecord created = eventRecordService.createEvent(event);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventRecord> getEventById(@PathVariable Long id) {
        return eventRecordService.getEventById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public ResponseEntity<List<EventRecord>> getAllEvents() {
        return ResponseEntity.ok(eventRecordService.getAllEvents());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<EventRecord> updateEventStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        EventRecord updated = eventRecordService.updateEventStatus(id, active);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/lookup/{eventCode}")
    public ResponseEntity<EventRecord> getEventByCode(@PathVariable String eventCode) {
        return eventRecordService.getEventByCode(eventCode)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}













// package com.example.demo.controller;

// import com.example.demo.model.EventRecord;
// import com.example.demo.service.EventRecordService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/events")
// public class EventRecordController {

//     private final EventRecordService eventService;

//     public EventRecordController(EventRecordService eventService) {
//         this.eventService = eventService;
//     }

//     @PostMapping
//     public EventRecord create(@RequestBody EventRecord event) {
//         return eventService.createEvent(event);
//     }

//     @GetMapping("/{id}")
//     public EventRecord getById(@PathVariable Long id) {
//         return eventService.getEventById(id);
//     }

//     @GetMapping
//     public List<EventRecord> getAll() {
//         return eventService.getAllEvents();
//     }

//     @PutMapping("/{id}/status")
//     public EventRecord updateStatus(@PathVariable Long id, @RequestParam boolean active) {
//         return eventService.updateEventStatus(id, active);
//     }

//     @GetMapping("/lookup/{eventCode}")
//     public EventRecord getByCode(@PathVariable String eventCode) {
//         return eventService.getEventByCode(eventCode).orElseThrow();
//     }
// }

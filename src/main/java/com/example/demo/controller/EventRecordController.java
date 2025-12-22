package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
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
    
    @GetMapping
    public ResponseEntity<List<EventRecord>> getAllEvents() {
        List<EventRecord> events = eventRecordService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventRecord> getEventById(@PathVariable Long id) {
        EventRecord event = eventRecordService.getEventById(id);
        return ResponseEntity.ok(event);
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<EventRecord> getEventByCode(@PathVariable String code) {
        Optional<EventRecord> event = eventRecordService.getEventByCode(code);
        return event.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<EventRecord> updateEventStatus(
            @PathVariable Long id, 
            @RequestParam Boolean active) {
        EventRecord updated = eventRecordService.updateEventStatus(id, active);
        return ResponseEntity.ok(updated);
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

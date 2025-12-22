package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventRecordController {
    
    @Autowired
    private EventRecordService eventService;
    
    @PostMapping
    public ResponseEntity<EventRecord> createEvent(@RequestBody EventRecord event) {
        EventRecord createdEvent = eventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventRecord> getEventById(@PathVariable Long id) {
        EventRecord event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }
    
    @GetMapping
    public ResponseEntity<List<EventRecord>> getAllEvents() {
        List<EventRecord> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<EventRecord> updateEventStatus(@PathVariable Long id, 
                                                         @RequestParam boolean active) {
        EventRecord event = eventService.updateEventStatus(id, active);
        return ResponseEntity.ok(event);
    }
    
    @GetMapping("/lookup/{eventCode}")
    public ResponseEntity<EventRecord> getEventByCode(@PathVariable String eventCode) {
        EventRecord event = eventService.getEventByCode(eventCode);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
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

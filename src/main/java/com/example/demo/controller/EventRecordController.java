package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.model.EventRecord;
import com.example.demo.service.impl.EventRecordServiceimpl;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Management")
public class EventRecordController {

    private final EventRecordServiceImpl service;

    public EventRecordController(EventRecordServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public EventRecord create(@RequestBody EventRecord event) {
        return service.createEvent(event);
    }

    @GetMapping("/{id}")
    public EventRecord getById(@PathVariable Long id) {
        return service.getEventById(id);
    }

    @GetMapping
    public List<EventRecord> getAll() {
        return service.getAllEvents();
    }

    @PutMapping("/{id}/status")
    public EventRecord updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return service.updateEventStatus(id, active);
    }

    @GetMapping("/lookup/{eventCode}")
    public EventRecord getByCode(@PathVariable String eventCode) {
        return service.getEventByCode(eventCode);
    }
}

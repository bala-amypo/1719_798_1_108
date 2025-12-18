package com.example.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.model.EventRecord;
import com.example.demo.exception.BadRequestException;

@Service
public class EventRecordServiceImpl {

    private final EventRecordRepository repo;

    public EventRecordServiceImpl(EventRecordRepository repo) {
        this.repo = repo;
    }

    public EventRecord createEvent(EventRecord event) {
        if (repo.existsByEventCode(event.getEventCode()))
            throw new BadRequestException("Event code already exists");
        if (event.getBasePrice() <= 0)
            throw new BadRequestException("Base price must be > 0");
        return repo.save(event);
    }

    public EventRecord getEventById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public EventRecord getEventByCode(String code) {
        return repo.findByEventCode(code).orElseThrow();
    }

    public List<EventRecord> getAllEvents() {
        return repo.findAll();
    }

    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord e = getEventById(id);
        e.setActive(active);
        return repo.save(e);
    }
}

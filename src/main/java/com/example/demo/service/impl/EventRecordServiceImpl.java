package com.example.demo.service.impl;

import com.example.demo.entity.EventRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRecordServiceImpl implements EventRecordService {

    private final EventRecordRepository eventRecordRepository;

    public EventRecordServiceImpl(EventRecordRepository eventRecordRepository) {
        this.eventRecordRepository = eventRecordRepository;
    }

    public EventRecord createEvent(EventRecord event) {
        if (eventRecordRepository.existsByEventCode(event.getEventCode())) {
            throw new BadRequestException("Event code already exists");
        }
        if (event.getBasePrice() == null || event.getBasePrice() <= 0) {
            throw new BadRequestException("Base price must be > 0");
        }
        return eventRecordRepository.save(event);
    }

    public EventRecord getEventById(Long id) {
        return eventRecordRepository.findById(id).orElse(null);
    }

    public EventRecord getEventByCode(String eventCode) {
        return eventRecordRepository.findByEventCode(eventCode).orElse(null);
    }

    public List<EventRecord> getAllEvents() {
        return eventRecordRepository.findAll();
    }

    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord event = getEventById(id);
        event.setActive(active);
        return eventRecordRepository.save(event);
    }
}

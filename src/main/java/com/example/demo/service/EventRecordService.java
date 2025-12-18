package com.example.demo.service;

import com.example.demo.entity.EventRecord;
import java.util.List;

public interface EventRecordService {
    EventRecord createEvent(EventRecord event);
    EventRecord getEventById(Long id);
    EventRecord getEventByCode(String eventCode);
    List<EventRecord> getAllEvents();
    EventRecord updateEventStatus(Long id, boolean active);
}

package com.example.demo.service.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventRecordImpl implements EventRecordService {
    
    private final EventRecordRepository eventRecordRepository;
    
    @Autowired
    public EventRecordImpl(EventRecordRepository eventRecordRepository) {
        this.eventRecordRepository = eventRecordRepository;
    }
    
    @Override
    public EventRecord createEvent(EventRecord event) {
        // Check for duplicate event code
        if (eventRecordRepository.existsByEventCode(event.getEventCode())) {
            throw new RuntimeException("Event code already exists");
        }
        
        // Validate base price
        if (event.getBasePrice() == null || event.getBasePrice() <= 0) {
            throw new RuntimeException("Base price must be > 0");
        }
        
        return eventRecordRepository.save(event);
    }
    
    @Override
    public EventRecord getEventById(Long id) {
        return eventRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
    
    @Override
    public EventRecord getEventByCode(String eventCode) {
        return eventRecordRepository.findByEventCode(eventCode);
    }
    
    @Override
    public List<EventRecord> getAllEvents() {
        return eventRecordRepository.findAll();
    }
    
    @Override
    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord event = getEventById(id);
        event.setActive(active);
        return eventRecordRepository.save(event);
    }
}







// package com.example.demo.service.impl;

// import com.example.demo.exception.BadRequestException;
// import com.example.demo.model.EventRecord;
// import com.example.demo.repository.EventRecordRepository;
// import com.example.demo.service.EventRecordService;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class EventRecordServiceImpl implements EventRecordService {

//     private final EventRecordRepository eventRecordRepository;

//     public EventRecordServiceImpl(EventRecordRepository eventRecordRepository) {
//         this.eventRecordRepository = eventRecordRepository;
//     }

//     @Override
//     public EventRecord createEvent(EventRecord event) {
//         if (eventRecordRepository.existsByEventCode(event.getEventCode())) {
//             throw new BadRequestException("Event code already exists");
//         }
//         if (event.getBasePrice() <= 0) {
//             throw new BadRequestException("Base price must be > 0");
//         }
//         return eventRecordRepository.save(event);
//     }

//     @Override
//     public EventRecord getEventById(Long id) {
//         return eventRecordRepository.findById(id).orElseThrow();
//     }

//     @Override
//     public Optional<EventRecord> getEventByCode(String eventCode) {
//         return eventRecordRepository.findByEventCode(eventCode);
//     }

//     @Override
//     public List<EventRecord> getAllEvents() {
//         return eventRecordRepository.findAll();
//     }

//     @Override
//     public EventRecord updateEventStatus(Long id, boolean active) {
//         EventRecord event = getEventById(id);
//         event.setActive(active);
//         return eventRecordRepository.save(event);
//     }
// }

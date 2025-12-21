package com.example.demo.service;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class EventRecordService {
    private final EventRecordRepository eventRecordRepository;
    
    public EventRecordService(EventRecordRepository eventRecordRepository) {
        this.eventRecordRepository = eventRecordRepository;
    }
    
    @Transactional
    public EventRecord createEvent(EventRecord event) {
        // Check for duplicate event code
        if (eventRecordRepository.existsByEventCode(event.getEventCode())) {
            throw new BadRequestException("Event code already exists");
        }
        
        // Validate base price
        if (event.getBasePrice() == null || event.getBasePrice() <= 0) {
            throw new BadRequestException("Base price must be > 0");
        }
        
        event.setActive(true);
        return eventRecordRepository.save(event);
    }
    
    public Optional<EventRecord> getEventById(Long id) {
        return eventRecordRepository.findById(id);
    }
    
    public Optional<EventRecord> getEventByCode(String eventCode) {
        return eventRecordRepository.findByEventCode(eventCode);
    }
    
    public List<EventRecord> getAllEvents() {
        return eventRecordRepository.findAll();
    }
    
    @Transactional
    public void updateEventStatus(Long id, boolean active) {
        EventRecord event = eventRecordRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Event not found"));
        event.setActive(active);
        eventRecordRepository.save(event);
    }
}










// package com.example.demo.service;

// import com.example.demo.model.EventRecord;

// import java.util.List;
// import java.util.Optional;

// public interface EventRecordService {

//     EventRecord createEvent(EventRecord event);

//     EventRecord getEventById(Long id);

//     Optional<EventRecord> getEventByCode(String eventCode);

//     List<EventRecord> getAllEvents();

//     EventRecord updateEventStatus(Long id, boolean active);
// }

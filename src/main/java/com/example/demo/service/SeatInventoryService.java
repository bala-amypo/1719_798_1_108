package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.SeatInventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatInventoryService {

    private final SeatInventoryRepository repository;

    public SeatInventoryService(SeatInventoryRepository repository) {
        this.repository = repository;
    }

    public SeatInventoryRecord getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public SeatInventoryRecord save(SeatInventoryRecord record) {
        return repository.save(record);
    }
}

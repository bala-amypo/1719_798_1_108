package com.example.demo.service.impl;
import com.example.demo.entity.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {

    private final PriceAdjustmentLogRepository repository;

    public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository repository) {
        this.repository = repository;
    }

    public PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log) {
        return repository.save(log);
    }

    public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
        return repository.findByEventId(eventId);
    }

    public List<PriceAdjustmentLog> getAllAdjustments() {
        return repository.findAll();
    }
}

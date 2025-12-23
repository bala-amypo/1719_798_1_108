package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {
    
    private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;
    
    @Override
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
        // Implement logic to get adjustments by event ID
        return priceAdjustmentLogRepository.findByEventId(eventId);
    }
    
    @Override
    public List<PriceAdjustmentLog> getAllAdjustments() {
        // Implement logic to get all adjustments
        return priceAdjustmentLogRepository.findAll();
    }
    
    @Override
    public PriceAdjustmentLog logAdjustment(PriceAdjustmentLog adjustment) {
        // Implement logic to save adjustment log
        return priceAdjustmentLogRepository.save(adjustment);
    }
}


// package com.example.demo.service.impl;
// import com.example.demo.model.PriceAdjustmentLog;
// import com.example.demo.repository.PriceAdjustmentLogRepository;
// import com.example.demo.service.PriceAdjustmentLogService;
// import org.springframework.stereotype.Service;
// import java.util.List;
// @Service
// public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {
//     private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;
//     public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository priceAdjustmentLogRepository) {
//         this.priceAdjustmentLogRepository = priceAdjustmentLogRepository;
//     }
//     @Override
//     public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
//         return priceAdjustmentLogRepository.findByEventId(eventId);
//     }
// }
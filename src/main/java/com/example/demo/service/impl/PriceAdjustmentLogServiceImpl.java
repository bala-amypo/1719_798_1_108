package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {
    
    private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;
    
    public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository priceAdjustmentLogRepository) {
        this.priceAdjustmentLogRepository = priceAdjustmentLogRepository;
    }
    
    @Override
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
        return priceAdjustmentLogRepository.findByEventId(eventId);
    }
}













// package com.example.demo.service.impl;

// import com.example.demo.model.PriceAdjustmentLog;
// import com.example.demo.repository.PriceAdjustmentLogRepository;
// import com.example.demo.service.PriceAdjustmentLogService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import java.util.List;

// @Service
// public class PriceAdjustmentLogImpl implements PriceAdjustmentLogService {
    
//     private final PriceAdjustmentLogRepository adjustmentLogRepository;
    
//     @Autowired
//     public PriceAdjustmentLogImpl(PriceAdjustmentLogRepository adjustmentLogRepository) {
//         this.adjustmentLogRepository = adjustmentLogRepository;
//     }
    
//     @Override
//     public PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log) {
//         return adjustmentLogRepository.save(log);
//     }
    
//     @Override
//     public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
//         return adjustmentLogRepository.findByEventId(eventId);
//     }
    
//     @Override
//     public List<PriceAdjustmentLog> getAllAdjustments() {
//         return adjustmentLogRepository.findAll();
//     }

//     // @Override
//     // public Optional<PriceAdjustmentLog> getAdjustmentById(Long id)
//     // {
        
//     // }
// }















// // package com.example.demo.service.impl;

// // import com.example.demo.model.PriceAdjustmentLog;
// // import com.example.demo.repository.PriceAdjustmentLogRepository;
// // import com.example.demo.service.PriceAdjustmentLogService;
// // import org.springframework.stereotype.Service;

// // import java.util.List;

// // @Service
// // public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {

// //     private final PriceAdjustmentLogRepository logRepository;

// //     public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository logRepository) {
// //         this.logRepository = logRepository;
// //     }

// //     @Override
// //     public PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log) {
// //         return logRepository.save(log);
// //     }

// //     @Override
// //     public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
// //         return logRepository.findByEventId(eventId);
// //     }

// //     @Override
// //     public List<PriceAdjustmentLog> getAllAdjustments() {
// //         return logRepository.findAll();
// //     }
// // }

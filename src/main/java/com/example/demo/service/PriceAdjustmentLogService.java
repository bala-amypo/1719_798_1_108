package com.example.demo.service;

import com.example.demo.model.PriceAdjustmentLog;
import java.util.List;

public interface PriceAdjustmentLogService {
    List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId);
}


// package com.example.demo.service;

// import com.example.demo.model.PriceAdjustmentLog;
// import java.util.List;

// public interface PriceAdjustmentLogService {
//     List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId);
//     List<PriceAdjustmentLog> getAllAdjustments();
//     PriceAdjustmentLog logAdjustment(PriceAdjustmentLog adjustment);
// }
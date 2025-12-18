package com.example.demo.service;

import com.example.demo.entity.PriceAdjustmentLog;
import java.util.List;

public interface PriceAdjustmentLogService {
    PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log);
    List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId);
    List<PriceAdjustmentLog> getAllAdjustments();
}

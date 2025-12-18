package com.example.demo.service;

import com.example.demo.entity.DynamicPriceRecord;
import java.util.List;

public interface DynamicPricingEngineService {
    DynamicPriceRecord computeDynamicPrice(Long eventId);
    List<DynamicPriceRecord> getPriceHistory(Long eventId);
    DynamicPriceRecord getLatestPrice(Long eventId);
    List<DynamicPriceRecord> getAllComputedPrices();
}

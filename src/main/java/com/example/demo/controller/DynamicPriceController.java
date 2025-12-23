package com.example.demo.controller;
import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/dynamic-prices")
public class DynamicPriceController {
    private final DynamicPricingEngineService dynamicPricingEngineService;
    public DynamicPriceController(DynamicPricingEngineService dynamicPricingEngineService) {
        this.dynamicPricingEngineService = dynamicPricingEngineService;
    }
    @PostMapping("/events/{eventId}/compute")
    public ResponseEntity<DynamicPriceRecord> computeDynamicPrice(@PathVariable Long eventId) {
        DynamicPriceRecord record = dynamicPricingEngineService.computeDynamicPrice(eventId);
        return ResponseEntity.ok(record);
    }
    @GetMapping("/events/{eventId}/history")
    public ResponseEntity<List<DynamicPriceRecord>> getPriceHistory(@PathVariable Long eventId) {
        List<DynamicPriceRecord> history = dynamicPricingEngineService.getPriceHistory(eventId);
        return ResponseEntity.ok(history);
    }
    @GetMapping
    public ResponseEntity<List<DynamicPriceRecord>> getAllComputedPrices() {
        List<DynamicPriceRecord> prices = dynamicPricingEngineService.getAllComputedPrices();
        return ResponseEntity.ok(prices);
    }
}
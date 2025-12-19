package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dynamic-pricing")
public class DynamicPricingController {

    private final DynamicPricingEngineService pricingService;

    public DynamicPricingController(DynamicPricingEngineService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping("/compute/{eventId}")
    public DynamicPriceRecord compute(@PathVariable Long eventId) {
        return pricingService.computeDynamicPrice(eventId);
    }

    @GetMapping("/latest/{eventId}")
    public DynamicPriceRecord latest(@PathVariable Long eventId) {
        return pricingService.getLatestPrice(eventId).orElseThrow();
    }

    @GetMapping("/history/{eventId}")
    public List<DynamicPriceRecord> history(@PathVariable Long eventId) {
        return pricingService.getPriceHistory(eventId);
    }

    @GetMapping
    public List<DynamicPriceRecord> getAll() {
        return pricingService.getAllComputedPrices();
    }
}

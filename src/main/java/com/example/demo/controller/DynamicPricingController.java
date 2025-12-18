package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.impl.DynamicPricingEngineServiceimpl;

@RestController
@RequestMapping("/api/dynamic-pricing")
@Tag(name = "Dynamic Pricing Engine")
public class DynamicPricingController {

    private final DynamicPricingEngineServiceImpl service;

    public DynamicPricingController(DynamicPricingEngineServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/compute/{eventId}")
    public DynamicPriceRecord compute(@PathVariable Long eventId) {
        return service.computeDynamicPrice(eventId);
    }

    @GetMapping("/latest/{eventId}")
    public DynamicPriceRecord latest(@PathVariable Long eventId) {
        return service.getLatestPrice(eventId);
    }

    @GetMapping("/history/{eventId}")
    public List<DynamicPriceRecord> history(@PathVariable Long eventId) {
        return service.getPriceHistory(eventId);
    }

    @GetMapping
    public List<DynamicPriceRecord> all() {
        return service.getAllComputedPrices();
    }
}

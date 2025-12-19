package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-adjustments")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogService logService;

    public PriceAdjustmentLogController(PriceAdjustmentLogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public PriceAdjustmentLog create(@RequestBody PriceAdjustmentLog log) {
        return logService.logAdjustment(log);
    }

    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getByEvent(@PathVariable Long eventId) {
        return logService.getAdjustmentsByEvent(eventId);
    }

    @GetMapping
    public List<PriceAdjustmentLog> getAll() {
        return logService.getAllAdjustments();
    }
}

package com.example.demo.controller;

import com.example.demo.entity.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-adjustments")
@Tag(name = "Price Adjustments")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogService service;

    public PriceAdjustmentLogController(PriceAdjustmentLogService service) {
        this.service = service;
    }

    @PostMapping
    public PriceAdjustmentLog create(@RequestBody PriceAdjustmentLog log) {
        return service.logAdjustment(log);
    }

    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getByEvent(@PathVariable Long eventId) {
        return service.getAdjustmentsByEvent(eventId);
    }

    @GetMapping
    public List<PriceAdjustmentLog> getAll() {
        return service.getAllAdjustments();
    }

    @GetMapping("/{id}")
    public PriceAdjustmentLog getById(@PathVariable Long id) {
        return service.getAllAdjustments()
                .stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

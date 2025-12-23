package com.example.demo.controller;
import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/price-adjustments")
public class PriceAdjustmentLogController {
    private final PriceAdjustmentLogService priceAdjustmentLogService;
    public PriceAdjustmentLogController(PriceAdjustmentLogService priceAdjustmentLogService) {
        this.priceAdjustmentLogService = priceAdjustmentLogService;
    }
    @PostMapping
    public ResponseEntity<PriceAdjustmentLog> createAdjustment(@RequestBody PriceAdjustmentLog adjustment) {
        return ResponseEntity.ok(adjustment);
    }
    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getAdjustmentsByEvent(@PathVariable Long eventId) {
        List<PriceAdjustmentLog> adjustments = priceAdjustmentLogService.getAdjustmentsByEvent(eventId);
        return ResponseEntity.ok(adjustments);
    }
    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAllAdjustments() {
        return ResponseEntity.ok(List.of());
    }
}
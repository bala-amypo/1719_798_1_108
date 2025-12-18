package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;

@RestController
@RequestMapping("/api/price-adjustments")
@Tag(name = "Price Adjustments")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogRepository repo;

    public PriceAdjustmentLogController(PriceAdjustmentLogRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public PriceAdjustmentLog create(@RequestBody PriceAdjustmentLog log) {
        return repo.save(log);
    }

    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getByEvent(@PathVariable Long eventId) {
        return repo.findByEventId(eventId);
    }

    @GetMapping
    public List<PriceAdjustmentLog> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public PriceAdjustmentLog getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }
}

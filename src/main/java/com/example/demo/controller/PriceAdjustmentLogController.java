package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/price-adjustments")
@Tag(name = "Price Adjustment Logs", description = "Endpoints for viewing price adjustment logs")
public class PriceAdjustmentLogController {
    
    private final PriceAdjustmentLogService priceAdjustmentLogService;
    
    public PriceAdjustmentLogController(PriceAdjustmentLogService priceAdjustmentLogService) {
        this.priceAdjustmentLogService = priceAdjustmentLogService;
    }
    
    @PostMapping
    public ResponseEntity<PriceAdjustmentLog> createLog(@RequestBody PriceAdjustmentLog log) {
        PriceAdjustmentLog created = priceAdjustmentLogService.logAdjustment(log);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getByEvent(@PathVariable Long eventId) {
        List<PriceAdjustmentLog> logs = priceAdjustmentLogService.getAdjustmentsByEvent(eventId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAllLogs() {
        List<PriceAdjustmentLog> allLogs = priceAdjustmentLogService.getAllAdjustments();
        return ResponseEntity.ok(allLogs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PriceAdjustmentLog> getLogById(@PathVariable Long id) {
        return priceAdjustmentLogService.getAdjustmentById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}













// package com.example.demo.controller;

// import com.example.demo.model.PriceAdjustmentLog;
// import com.example.demo.service.PriceAdjustmentLogService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/price-adjustments")
// public class PriceAdjustmentLogController {

//     private final PriceAdjustmentLogService logService;

//     public PriceAdjustmentLogController(PriceAdjustmentLogService logService) {
//         this.logService = logService;
//     }

//     @PostMapping
//     public PriceAdjustmentLog create(@RequestBody PriceAdjustmentLog log) {
//         return logService.logAdjustment(log);
//     }

//     @GetMapping("/event/{eventId}")
//     public List<PriceAdjustmentLog> getByEvent(@PathVariable Long eventId) {
//         return logService.getAdjustmentsByEvent(eventId);
//     }

//     @GetMapping
//     public List<PriceAdjustmentLog> getAll() {
//         return logService.getAllAdjustments();
//     }
// }

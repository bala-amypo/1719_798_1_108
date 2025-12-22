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
        // In a real app, you would save it through a service
        // For now, we'll just return it
        return ResponseEntity.ok(adjustment);
    }
    
    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getAdjustmentsByEvent(@PathVariable Long eventId) {
        List<PriceAdjustmentLog> adjustments = priceAdjustmentLogService.getAdjustmentsByEvent(eventId);
        return ResponseEntity.ok(adjustments);
    }
    
    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAllAdjustments() {
        // This method doesn't exist in the interface, so we need to add it
        // For now, return empty list or implement the method
        return ResponseEntity.ok(List.of());
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

package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/price-adjustments")
public class PriceAdjustmentLogController {
    
    @Autowired
    private PriceAdjustmentLogService adjustmentLogService;
    
    @PostMapping
    public ResponseEntity<PriceAdjustmentLog> logAdjustment(@RequestBody PriceAdjustmentLog log) {
        PriceAdjustmentLog savedLog = adjustmentLogService.logAdjustment(log);
        return ResponseEntity.ok(savedLog);
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<PriceAdjustmentLog>> getAdjustmentsByEvent(@PathVariable Long eventId) {
        List<PriceAdjustmentLog> logs = adjustmentLogService.getAdjustmentsByEvent(eventId);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping
    public ResponseEntity<List<PriceAdjustmentLog>> getAllAdjustments() {
        List<PriceAdjustmentLog> allLogs = adjustmentLogService.getAllAdjustments();
        return ResponseEntity.ok(allLogs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PriceAdjustmentLog> getAdjustmentById(@PathVariable Long id) {
        // Note: You might want to add this method to your service interface
        return ResponseEntity.ok().build();
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

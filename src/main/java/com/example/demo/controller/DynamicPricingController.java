package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dynamic-pricing")
@Tag(name = "Dynamic Pricing", description = "Endpoints for dynamic price computation")
public class DynamicPricingController {
    
    private final DynamicPricingEngineService dynamicPricingEngineService;
    
    public DynamicPricingController(DynamicPricingEngineService dynamicPricingEngineService) {
        this.dynamicPricingEngineService = dynamicPricingEngineService;
    }
    
    @PostMapping("/compute/{eventId}")
    public ResponseEntity<DynamicPriceRecord> computePrice(@PathVariable Long eventId) {
        DynamicPriceRecord result = dynamicPricingEngineService.computeDynamicPrice(eventId);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/latest/{eventId}")
    public ResponseEntity<DynamicPriceRecord> getLatestPrice(@PathVariable Long eventId) {
        return dynamicPricingEngineService.getLatestPrice(eventId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/history/{eventId}")
    public ResponseEntity<List<DynamicPriceRecord>> getPriceHistory(@PathVariable Long eventId) {
        List<DynamicPriceRecord> history = dynamicPricingEngineService.getPriceHistory(eventId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping
    public ResponseEntity<List<DynamicPriceRecord>> getAllComputedPrices() {
        List<DynamicPriceRecord> allPrices = dynamicPricingEngineService.getAllComputedPrices();
        return ResponseEntity.ok(allPrices);
    }
}












// package com.example.demo.controller;

// import com.example.demo.model.DynamicPriceRecord;
// import com.example.demo.service.DynamicPricingEngineService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/dynamic-pricing")
// public class DynamicPricingController {

//     private final DynamicPricingEngineService pricingService;

//     public DynamicPricingController(DynamicPricingEngineService pricingService) {
//         this.pricingService = pricingService;
//     }

//     @PostMapping("/compute/{eventId}")
//     public DynamicPriceRecord compute(@PathVariable Long eventId) {
//         return pricingService.computeDynamicPrice(eventId);
//     }

//     @GetMapping("/latest/{eventId}")
//     public DynamicPriceRecord latest(@PathVariable Long eventId) {
//         return pricingService.getLatestPrice(eventId).orElseThrow();
//     }

//     @GetMapping("/history/{eventId}")
//     public List<DynamicPriceRecord> history(@PathVariable Long eventId) {
//         return pricingService.getPriceHistory(eventId);
//     }

//     @GetMapping
//     public List<DynamicPriceRecord> getAll() {
//         return pricingService.getAllComputedPrices();
//     }
// }

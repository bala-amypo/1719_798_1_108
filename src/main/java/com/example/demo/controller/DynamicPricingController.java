package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPriceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dynamic-pricing")
public class DynamicPriceController {
    
    @Autowired
    private DynamicPriceRecordService dynamicPriceService;
    
    @PostMapping("/compute/{eventId}")
    public ResponseEntity<DynamicPriceRecord> computePrice(@PathVariable Long eventId) {
        DynamicPriceRecord priceRecord = dynamicPriceService.computeDynamicPrice(eventId);
        return ResponseEntity.ok(priceRecord);
    }
    
    @GetMapping("/latest/{eventId}")
    public ResponseEntity<DynamicPriceRecord> getLatestPrice(@PathVariable Long eventId) {
        Optional<DynamicPriceRecord> priceRecord = dynamicPriceService.getLatestPrice(eventId);
        return priceRecord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/history/{eventId}")
    public ResponseEntity<List<DynamicPriceRecord>> getPriceHistory(@PathVariable Long eventId) {
        List<DynamicPriceRecord> priceHistory = dynamicPriceService.getPriceHistory(eventId);
        return ResponseEntity.ok(priceHistory);
    }
    
    @GetMapping
    public ResponseEntity<List<DynamicPriceRecord>> getAllComputedPrices() {
        List<DynamicPriceRecord> allPrices = dynamicPriceService.getAllComputedPrices();
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

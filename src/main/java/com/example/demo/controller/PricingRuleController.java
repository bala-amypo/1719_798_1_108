package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
@Tag(name = "Pricing Rule Management", description = "Endpoints for managing pricing rules")
public class PricingRuleController {
    
    private final PricingRuleService pricingRuleService;
    
    public PricingRuleController(PricingRuleService pricingRuleService) {
        this.pricingRuleService = pricingRuleService;
    }
    
    @PostMapping
    public ResponseEntity<PricingRule> createRule(@RequestBody PricingRule rule) {
        PricingRule created = pricingRuleService.createRule(rule);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PricingRule> updateRule(
            @PathVariable Long id,
            @RequestBody PricingRule updatedRule) {
        PricingRule updated = pricingRuleService.updateRule(id, updatedRule);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<PricingRule>> getActiveRules() {
        List<PricingRule> activeRules = pricingRuleService.getActiveRules();
        return ResponseEntity.ok(activeRules);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PricingRule> getRule(@PathVariable Long id) {
        return ResponseEntity.ok().build(); // Implement as needed
    }
    
    @GetMapping
    public ResponseEntity<List<PricingRule>> getAllRules() {
        List<PricingRule> rules = pricingRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
}













// package com.example.demo.controller;

// import com.example.demo.model.PricingRule;
// import com.example.demo.service.PricingRuleService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/pricing-rules")
// public class PricingRuleController {

//     private final PricingRuleService ruleService;

//     public PricingRuleController(PricingRuleService ruleService) {
//         this.ruleService = ruleService;
//     }

//     @PostMapping
//     public PricingRule create(@RequestBody PricingRule rule) {
//         return ruleService.createRule(rule);
//     }

//     @PutMapping("/{id}")
//     public PricingRule update(@PathVariable Long id, @RequestBody PricingRule rule) {
//         return ruleService.updateRule(id, rule);
//     }

//     @GetMapping("/active")
//     public List<PricingRule> getActive() {
//         return ruleService.getActiveRules();
//     }

//     @GetMapping("/{id}")
//     public PricingRule getById(@PathVariable Long id) {
//         return ruleService.getAllRules()
//                 .stream()
//                 .filter(r -> r.getId().equals(id))
//                 .findFirst()
//                 .orElseThrow();
//     }

//     @GetMapping
//     public List<PricingRule> getAll() {
//         return ruleService.getAllRules();
//     }
// }

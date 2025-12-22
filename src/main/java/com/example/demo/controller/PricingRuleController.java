package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
public class PricingRuleController {
    
    @Autowired
    private PricingRuleService pricingRuleService;
    
    @PostMapping
    public ResponseEntity<PricingRule> createRule(@RequestBody PricingRule rule) {
        PricingRule createdRule = pricingRuleService.createRule(rule);
        return ResponseEntity.ok(createdRule);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PricingRule> updateRule(@PathVariable Long id, 
                                                  @RequestBody PricingRule updatedRule) {
        PricingRule rule = pricingRuleService.updateRule(id, updatedRule);
        return ResponseEntity.ok(rule);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<PricingRule>> getActiveRules() {
        List<PricingRule> rules = pricingRuleService.getActiveRules();
        return ResponseEntity.ok(rules);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PricingRule> getRuleById(@PathVariable Long id) {
        // Note: You might want to add this method to your service interface
        return ResponseEntity.ok().build();
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

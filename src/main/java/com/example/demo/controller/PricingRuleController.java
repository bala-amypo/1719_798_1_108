package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.demo.model.PricingRule;
import com.example.demo.service.impl.PricingRuleServiceImpl;

@RestController
@RequestMapping("/api/pricing-rules")
@Tag(name = "Pricing Rules")
public class PricingRuleController {

    private final PricingRuleServiceImpl service;

    public PricingRuleController(PricingRuleServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public PricingRule create(@RequestBody PricingRule rule) {
        return service.createRule(rule);
    }

    @GetMapping("/active")
    public List<PricingRule> getActive() {
        return service.getActiveRules();
    }

    @GetMapping
    public List<PricingRule> getAll() {
        return service.getAllRules();
    }
}

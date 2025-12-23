package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingRuleServiceImpl implements PricingRuleService {
    
    private final PricingRuleRepository pricingRuleRepository;
    
    @Override
    public PricingRule createRule(PricingRule rule) {
        // Implement logic to create a new rule
        return pricingRuleRepository.save(rule);
    }
    
    @Override
    public List<PricingRule> getAllRules() {
        // Implement logic to get all rules
        return pricingRuleRepository.findAll();
    }
    
    @Override
    public List<PricingRule> getActiveRules() {
        // Implement logic to get active rules
        return pricingRuleRepository.findByIsActiveTrue();
    }
    
    @Override
    public PricingRule updateRule(Long id, PricingRule rule) {
        // Implement logic to update existing rule
        PricingRule existingRule = pricingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pricing rule not found with id: " + id));
        
        // Update the existing rule with new values
        existingRule.setName(rule.getName());
        existingRule.setDescription(rule.getDescription());
        existingRule.setRuleType(rule.getRuleType());
        existingRule.setAdjustmentValue(rule.getAdjustmentValue());
        existingRule.setCondition(rule.getCondition());
        existingRule.setIsActive(rule.getIsActive());
        existingRule.setPriority(rule.getPriority());
        
        return pricingRuleRepository.save(existingRule);
    }
}


// package com.example.demo.service;

// import com.example.demo.model.PricingRule;
// import java.util.List;

// public interface PricingRuleService {
//     PricingRule createRule(PricingRule rule);
//     List<PricingRule> getAllRules();
//     List<PricingRule> getActiveRules();
//     PricingRule updateRule(Long id, PricingRule rule);
// }
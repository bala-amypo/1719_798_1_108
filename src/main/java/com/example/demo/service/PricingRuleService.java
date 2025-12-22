package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PricingRuleService {
    
    private final PricingRuleRepository pricingRuleRepository;
    
    public PricingRuleService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }
    
    public PricingRule createRule(PricingRule rule) {
        if (rule.getPriceMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        
        if (pricingRuleRepository.existsByRuleCode(rule.getRuleCode())) {
            throw new BadRequestException("Rule code already exists");
        }
        
        return pricingRuleRepository.save(rule);
    }
    
    public PricingRule updateRule(Long id, PricingRule updatedRule) {
        PricingRule existingRule = pricingRuleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Rule not found"));
        
        if (updatedRule.getPriceMultiplier() != null && updatedRule.getPriceMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        
        existingRule.setDescription(updatedRule.getDescription());
        existingRule.setMinRemainingSeats(updatedRule.getMinRemainingSeats());
        existingRule.setMaxRemainingSeats(updatedRule.getMaxRemainingSeats());
        existingRule.setDaysBeforeEvent(updatedRule.getDaysBeforeEvent());
        existingRule.setPriceMultiplier(updatedRule.getPriceMultiplier());
        existingRule.setActive(updatedRule.getActive());
        
        return pricingRuleRepository.save(existingRule);
    }
    
    public List<PricingRule> getActiveRules() {
        return pricingRuleRepository.findByActiveTrue();
    }
    
    public Optional<PricingRule> getRuleByCode(String ruleCode) {
        return pricingRuleRepository.findByRuleCode(ruleCode);
    }
    
    public List<PricingRule> getAllRules() {
        return pricingRuleRepository.findAll();
    }
    
    public Optional<PricingRule> getRuleById(Long id) {
        return pricingRuleRepository.findById(id);
    }
}














// package com.example.demo.service;

// import com.example.demo.model.PricingRule;

// import java.util.List;
// import java.util.Optional;

// public interface PricingRuleService {

//     PricingRule createRule(PricingRule rule);

//     PricingRule updateRule(Long id, PricingRule updatedRule);

//     List<PricingRule> getActiveRules();

//     Optional<PricingRule> getRuleByCode(String ruleCode);

//     List<PricingRule> getAllRules();
// }

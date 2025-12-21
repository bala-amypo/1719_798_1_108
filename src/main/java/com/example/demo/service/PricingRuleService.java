package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PricingRuleService {
    
    private final PricingRuleRepository pricingRuleRepository;
    
    public PricingRuleService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }
    
    @Transactional
    public PricingRule createRule(PricingRule rule) {
        // Check for duplicate rule code
        if (pricingRuleRepository.existsByRuleCode(rule.getRuleCode())) {
            throw new BadRequestException("Rule code already exists");
        }
        
        // Validate multiplier
        if (rule.getPriceMultiplier() == null || rule.getPriceMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        
        rule.setActive(true);
        return pricingRuleRepository.save(rule);
    }
    
    @Transactional
    public PricingRule updateRule(Long id, PricingRule updatedRule) {
        PricingRule existingRule = pricingRuleRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Rule not found"));
        
        // Validate multiplier if being updated
        if (updatedRule.getPriceMultiplier() != null) {
            if (updatedRule.getPriceMultiplier() <= 0) {
                throw new BadRequestException("Price multiplier must be > 0");
            }
            existingRule.setPriceMultiplier(updatedRule.getPriceMultiplier());
        }
        
        if (updatedRule.getDescription() != null) {
            existingRule.setDescription(updatedRule.getDescription());
        }
        
        if (updatedRule.getMinRemainingSeats() != null) {
            existingRule.setMinRemainingSeats(updatedRule.getMinRemainingSeats());
        }
        
        if (updatedRule.getMaxRemainingSeats() != null) {
            existingRule.setMaxRemainingSeats(updatedRule.getMaxRemainingSeats());
        }
        
        if (updatedRule.getDaysBeforeEvent() != null) {
            existingRule.setDaysBeforeEvent(updatedRule.getDaysBeforeEvent());
        }
        
        if (updatedRule.getActive() != null) {
            existingRule.setActive(updatedRule.getActive());
        }
        
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

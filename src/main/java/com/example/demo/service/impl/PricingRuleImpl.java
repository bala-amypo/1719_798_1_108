package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PricingRuleImpl implements PricingRuleService {
    
    private final PricingRuleRepository pricingRuleRepository;
    
    @Autowired
    public PricingRuleImpl(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }
    
    @Override
    public PricingRule createRule(PricingRule rule) {
        // Check for duplicate rule code
        if (pricingRuleRepository.existsByRuleCode(rule.getRuleCode())) {
            throw new RuntimeException("Rule code already exists");
        }
        
        // Validate multiplier
        if (rule.getPriceMultiplier() == null || rule.getPriceMultiplier() <= 0) {
            throw new RuntimeException("Price multiplier must be > 0");
        }
        
        return pricingRuleRepository.save(rule);
    }
    
    @Override
    public PricingRule updateRule(Long id, PricingRule updatedRule) {
        PricingRule existingRule = pricingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pricing rule not found"));
        
        // Validate multiplier if provided
        if (updatedRule.getPriceMultiplier() != null && updatedRule.getPriceMultiplier() <= 0) {
            throw new RuntimeException("Price multiplier must be > 0");
        }
        
        // Update fields
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
        if (updatedRule.getPriceMultiplier() != null) {
            existingRule.setPriceMultiplier(updatedRule.getPriceMultiplier());
        }
        if (updatedRule.getActive() != null) {
            existingRule.setActive(updatedRule.getActive());
        }
        
        return pricingRuleRepository.save(existingRule);
    }
    
    @Override
    public List<PricingRule> getActiveRules() {
        return pricingRuleRepository.findByActiveTrue();
    }
    
    @Override
    public PricingRule getRuleByCode(String ruleCode) {
        return pricingRuleRepository.findByRuleCode(ruleCode);
    }
    
    @Override
    public List<PricingRule> getAllRules() {
        return pricingRuleRepository.findAll();
    }
}



















// package com.example.demo.service.impl;

// import com.example.demo.exception.BadRequestException;
// import com.example.demo.model.PricingRule;
// import com.example.demo.repository.PricingRuleRepository;
// import com.example.demo.service.PricingRuleService;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class PricingRuleServiceImpl implements PricingRuleService {

//     private final PricingRuleRepository ruleRepository;

//     public PricingRuleServiceImpl(PricingRuleRepository ruleRepository) {
//         this.ruleRepository = ruleRepository;
//     }

//     @Override
//     public PricingRule createRule(PricingRule rule) {
//         if (ruleRepository.existsByRuleCode(rule.getRuleCode())) {
//             throw new BadRequestException("Duplicate rule code");
//         }
//         if (rule.getPriceMultiplier() <= 0) {
//             throw new BadRequestException("Price multiplier must be > 0");
//         }
//         return ruleRepository.save(rule);
//     }

//     @Override
//     public PricingRule updateRule(Long id, PricingRule updatedRule) {
//         PricingRule rule = ruleRepository.findById(id).orElseThrow();
//         rule.setDescription(updatedRule.getDescription());
//         rule.setMinRemainingSeats(updatedRule.getMinRemainingSeats());
//         rule.setMaxRemainingSeats(updatedRule.getMaxRemainingSeats());
//         rule.setDaysBeforeEvent(updatedRule.getDaysBeforeEvent());
//         rule.setPriceMultiplier(updatedRule.getPriceMultiplier());
//         rule.setActive(updatedRule.getActive());
//         return ruleRepository.save(rule);
//     }

//     @Override
//     public List<PricingRule> getActiveRules() {
//         return ruleRepository.findByActiveTrue();
//     }

//     @Override
//     public Optional<PricingRule> getRuleByCode(String ruleCode) {
//         return ruleRepository.findAll()
//                 .stream()
//                 .filter(r -> r.getRuleCode().equals(ruleCode))
//                 .findFirst();
//     }

//     @Override
//     public List<PricingRule> getAllRules() {
//         return ruleRepository.findAll();
//     }
// }

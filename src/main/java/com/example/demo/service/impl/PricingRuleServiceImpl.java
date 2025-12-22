package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {
    
    private final PricingRuleRepository pricingRuleRepository;
    
    public PricingRuleServiceImpl(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }
    
    @Override
    public PricingRule createRule(PricingRule rule) {
        if (rule.getPriceMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        
        if (pricingRuleRepository.existsByRuleCode(rule.getRuleCode())) {
            throw new BadRequestException("Rule code already exists");
        }
        
        return pricingRuleRepository.save(rule);
    }
    
    @Override
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
    
    @Override
    public List<PricingRule> getActiveRules() {
        return pricingRuleRepository.findByActiveTrue();
    }
    
    @Override
    public Optional<PricingRule> getRuleByCode(String ruleCode) {
        return pricingRuleRepository.findByRuleCode(ruleCode);
    }
    
    @Override
    public List<PricingRule> getAllRules() {
        return pricingRuleRepository.findAll();
    }
    
    @Override
    public Optional<PricingRule> getRuleById(Long id) {
        return pricingRuleRepository.findById(id);
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

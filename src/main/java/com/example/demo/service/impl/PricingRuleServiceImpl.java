package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingRuleServiceImpl implements PricingRuleService {
    
    private final PricingRuleRepository pricingRuleRepository;
    
    @Override
    public PricingRule createRule(PricingRule rule) {
        return pricingRuleRepository.save(rule);
    }
    
    @Override
    public List<PricingRule> getAllRules() {
        return pricingRuleRepository.findAll();
    }
    
    @Override
    public List<PricingRule> getActiveRules() {
        return pricingRuleRepository.findByActiveTrue();
    }
    
    @Override
    public PricingRule updateRule(Long id, PricingRule rule) {
        PricingRule existingRule = pricingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pricing rule not found with id: " + id));
        
        // Update only non-null fields from the input rule
        if (rule.getRuleCode() != null) {
            existingRule.setRuleCode(rule.getRuleCode());
        }
        if (rule.getDescription() != null) {
            existingRule.setDescription(rule.getDescription());
        }
        if (rule.getMinRemainingSeats() != null) {
            existingRule.setMinRemainingSeats(rule.getMinRemainingSeats());
        }
        if (rule.getMaxRemainingSeats() != null) {
            existingRule.setMaxRemainingSeats(rule.getMaxRemainingSeats());
        }
        if (rule.getDaysBeforeEvent() != null) {
            existingRule.setDaysBeforeEvent(rule.getDaysBeforeEvent());
        }
        if (rule.getPriceMultiplier() != null) {
            existingRule.setPriceMultiplier(rule.getPriceMultiplier());
        }
        if (rule.getActive() != null) {
            existingRule.setActive(rule.getActive());
        }
        
        return pricingRuleRepository.save(existingRule);
    }
}

// package com.example.demo.service.impl;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.model.PricingRule;
// import com.example.demo.repository.PricingRuleRepository;
// import com.example.demo.service.PricingRuleService;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import java.util.List;
// @Service
// public class PricingRuleServiceImpl implements PricingRuleService {
//     private final PricingRuleRepository pricingRuleRepository;
//     public PricingRuleServiceImpl(PricingRuleRepository pricingRuleRepository) {
//         this.pricingRuleRepository = pricingRuleRepository;
//     }
//     @Override
//     @Transactional
//     public PricingRule createRule(PricingRule rule) {
//         if (rule.getRuleCode() == null || rule.getRuleCode().trim().isEmpty()) {
//             throw new BadRequestException("Rule code is required");
//         }   
//         if (rule.getPriceMultiplier() == null || rule.getPriceMultiplier() <= 0) {
//             throw new BadRequestException("Price multiplier must be > 0");
//         }
//         if (pricingRuleRepository.existsByRuleCode(rule.getRuleCode())) {
//             throw new BadRequestException("Rule code already exists: " + rule.getRuleCode());
//         }
//         return pricingRuleRepository.save(rule);
//     }
//     @Override
//     public List<PricingRule> getAllRules() {
//         return pricingRuleRepository.findAll();
//     }
//     @Override
//     public List<PricingRule> getActiveRules() {
//         return pricingRuleRepository.findByActiveTrue();
//     }
// }
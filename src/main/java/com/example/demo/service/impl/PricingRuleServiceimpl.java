package com.example.demo.service.impl;

import com.example.demo.entity.PricingRule;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository pricingRuleRepository;

    public PricingRuleServiceImpl(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }

    public PricingRule createRule(PricingRule rule) {
        if (pricingRuleRepository.existsByRuleCode(rule.getRuleCode())) {
            throw new BadRequestException("Event code already exists");
        }
        if (rule.getPriceMultiplier() == null || rule.getPriceMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return pricingRuleRepository.save(rule);
    }

    public PricingRule updateRule(Long id, PricingRule updatedRule) {
        PricingRule rule = pricingRuleRepository.findById(id).orElse(null);
        if (updatedRule.getPriceMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        rule.setDescription(updatedRule.getDescription());
        rule.setMinRemainingSeats(updatedRule.getMinRemainingSeats());
        rule.setMaxRemainingSeats(updatedRule.getMaxRemainingSeats());
        rule.setDaysBeforeEvent(updatedRule.getDaysBeforeEvent());
        rule.setPriceMultiplier(updatedRule.getPriceMultiplier());
        rule.setActive(updatedRule.getActive());
        return pricingRuleRepository.save(rule);
    }

    public List<PricingRule> getActiveRules() {
        return pricingRuleRepository.findByActiveTrue();
    }

    public PricingRule getRuleByCode(String ruleCode) {
        return pricingRuleRepository.findByRuleCode(ruleCode).orElse(null);
    }

    public List<PricingRule> getAllRules() {
        return pricingRuleRepository.findAll();
    }
}

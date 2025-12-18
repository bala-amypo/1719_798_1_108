package com.example.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.model.PricingRule;
import com.example.demo.exception.BadRequestException;

@Service
public class PricingRuleServiceImpl {

    private final PricingRuleRepository repo;

    public PricingRuleServiceImpl(PricingRuleRepository repo) {
        this.repo = repo;
    }

    public PricingRule createRule(PricingRule rule) {
        if (repo.existsByRuleCode(rule.getRuleCode()))
            throw new BadRequestException("Duplicate rule");
        if (rule.getPriceMultiplier() <= 0)
            throw new BadRequestException("Price multiplier must be > 0");
        return repo.save(rule);
    }

    public List<PricingRule> getActiveRules() {
        return repo.findByActiveTrue();
    }

    public List<PricingRule> getAllRules() {
        return repo.findAll();
    }
}

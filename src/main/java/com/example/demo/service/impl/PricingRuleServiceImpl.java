package com.example.demo.service.impl;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class PricingRuleServiceImpl implements PricingRuleService {
    private final PricingRuleRepository pricingRuleRepository;
    public PricingRuleServiceImpl(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
    }
    @Override
    @Transactional
    public PricingRule createRule(PricingRule rule) {
        if (rule.getRuleCode() == null || rule.getRuleCode().trim().isEmpty()) {
            throw new BadRequestException("Rule code is required");
        }   
        if (rule.getPriceMultiplier() == null || rule.getPriceMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        if (pricingRuleRepository.existsByRuleCode(rule.getRuleCode())) {
            throw new BadRequestException("Rule code already exists: " + rule.getRuleCode());
        }
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
}
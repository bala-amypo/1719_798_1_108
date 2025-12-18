package com.example.demo.service;

import com.example.demo.entity.PricingRule;
import java.util.List;

public interface PricingRuleService {
    PricingRule createRule(PricingRule rule);
    PricingRule updateRule(Long id, PricingRule updatedRule);
    List<PricingRule> getActiveRules();
    PricingRule getRuleByCode(String ruleCode);
    List<PricingRule> getAllRules();
}

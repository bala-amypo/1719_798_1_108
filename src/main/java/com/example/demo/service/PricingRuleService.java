package com.example.demo.service;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleService {
    PricingRule createRule(PricingRule rule);
    List<PricingRule> getAllRules();
    List<PricingRule> getActiveRules();
    PricingRule updateRule(Long id, PricingRule rule);
}







// package com.example.demo.service;

// import com.example.demo.model.PricingRule;
// import java.util.List;

// public interface PricingRuleService {
//     PricingRule createRule(PricingRule rule);
//     PricingRule updateRule(Long id, PricingRule updatedRule);
//     List<PricingRule> getActiveRules();
//     PricingRule getRuleByCode(String ruleCode);
//     List<PricingRule> getAllRules();
// }













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

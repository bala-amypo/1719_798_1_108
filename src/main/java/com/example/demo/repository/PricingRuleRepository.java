package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.example.demo.model.*;


public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    boolean existsByRuleCode(String code);
    List<PricingRule> findByActiveTrue();
}

package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PricingRule;

public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {

    boolean existsByRuleCode(String code);

    List<PricingRule> findByActiveTrue();

    Optional<PricingRule> findByRuleCode(String code);
}

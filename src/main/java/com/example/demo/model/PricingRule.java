package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pricing_rules", uniqueConstraints = @UniqueConstraint(columnNames = "ruleCode"))
public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleCode;
    private String description;
    private Integer minRemainingSeats;
    private Integer maxRemainingSeats;
    private Integer daysBeforeEvent;
    private Double priceMultiplier;
    private Boolean active;

    public PricingRule() {}

    public PricingRule(Long id, String ruleCode, String description,
                       Integer minRemainingSeats, Integer maxRemainingSeats,
                       Integer daysBeforeEvent, Double priceMultiplier, Boolean active) {
        this.id = id;
        this.ruleCode = ruleCode;
        this.description = description;
        this.minRemainingSeats = minRemainingSeats;
        this.maxRemainingSeats = maxRemainingSeats;
        this.daysBeforeEvent = daysBeforeEvent;
        this.priceMultiplier = priceMultiplier;
        this.active = active;
    }

    public String getRuleCode() { return ruleCode; }
    public Integer getMinRemainingSeats() { return minRemainingSeats; }
    public Integer getMaxRemainingSeats() { return maxRemainingSeats; }
    public Integer getDaysBeforeEvent() { return daysBeforeEvent; }
    public Double getPriceMultiplier() { return priceMultiplier; }
}

package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pricing_rules")
public class PricingRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_code", unique = true, nullable = false)
    private String ruleCode;
    
    @Column(nullable = false)
    private String description;
    
    @Column(name = "min_remaining_seats")
    private Integer minRemainingSeats;
    
    @Column(name = "max_remaining_seats")
    private Integer maxRemainingSeats;
    
    @Column(name = "days_before_event")
    private Integer daysBeforeEvent;
    
    @Column(name = "price_multiplier", nullable = false)
    private Double priceMultiplier;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public PricingRule() {}
    
    public PricingRule(String ruleCode, String description, Double priceMultiplier) {
        this.ruleCode = ruleCode;
        this.description = description;
        this.priceMultiplier = priceMultiplier;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRuleCode() {
        return ruleCode;
    }
    
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getMinRemainingSeats() {
        return minRemainingSeats;
    }
    
    public void setMinRemainingSeats(Integer minRemainingSeats) {
        this.minRemainingSeats = minRemainingSeats;
    }
    
    public Integer getMaxRemainingSeats() {
        return maxRemainingSeats;
    }
    
    public void setMaxRemainingSeats(Integer maxRemainingSeats) {
        this.maxRemainingSeats = maxRemainingSeats;
    }
    
    public Integer getDaysBeforeEvent() {
        return daysBeforeEvent;
    }
    
    public void setDaysBeforeEvent(Integer daysBeforeEvent) {
        this.daysBeforeEvent = daysBeforeEvent;
    }
    
    public Double getPriceMultiplier() {
        return priceMultiplier;
    }
    
    public void setPriceMultiplier(Double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
















// package com.example.demo.model;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "pricing_rules")
// public class PricingRule {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true)
//     private String ruleCode;

//     private String description;
//     private Integer minRemainingSeats;
//     private Integer maxRemainingSeats;
//     private Integer daysBeforeEvent;
//     private Double priceMultiplier;
//     private Boolean active;

//     public PricingRule() {
//     }

//     public PricingRule(Long id, String ruleCode, String description,
//                        Integer minRemainingSeats, Integer maxRemainingSeats,
//                        Integer daysBeforeEvent, Double priceMultiplier, Boolean active) {
//         this.id = id;
//         this.ruleCode = ruleCode;
//         this.description = description;
//         this.minRemainingSeats = minRemainingSeats;
//         this.maxRemainingSeats = maxRemainingSeats;
//         this.daysBeforeEvent = daysBeforeEvent;
//         this.priceMultiplier = priceMultiplier;
//         this.active = active;
//     }

//     public Long getId() { return id; }
//     public String getRuleCode() { return ruleCode; }
//     public String getDescription() { return description; }
//     public Integer getMinRemainingSeats() { return minRemainingSeats; }
//     public Integer getMaxRemainingSeats() { return maxRemainingSeats; }
//     public Integer getDaysBeforeEvent() { return daysBeforeEvent; }
//     public Double getPriceMultiplier() { return priceMultiplier; }
//     public Boolean getActive() { return active; }

//     public void setId(Long id) { this.id = id; }
//     public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }
//     public void setDescription(String description) { this.description = description; }
//     public void setMinRemainingSeats(Integer minRemainingSeats) { this.minRemainingSeats = minRemainingSeats; }
//     public void setMaxRemainingSeats(Integer maxRemainingSeats) { this.maxRemainingSeats = maxRemainingSeats; }
//     public void setDaysBeforeEvent(Integer daysBeforeEvent) { this.daysBeforeEvent = daysBeforeEvent; }
//     public void setPriceMultiplier(Double priceMultiplier) { this.priceMultiplier = priceMultiplier; }
//     public void setActive(Boolean active) { this.active = active; }
// }

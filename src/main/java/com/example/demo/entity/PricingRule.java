package com.example.demo.entity;
import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "ruleCode"))
public class PricingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ruleCode;
    private Integer minRemainingSeats;
    private Integer maxRemainingSeats;
    private Integer daysBeforeEvent;
    private Double priceMultiplier;
    private Boolean active;
    public String getRuleCode() {
         return ruleCode;
          }
    public Double getPriceMultiplier() {
         return priceMultiplier; 
         }
    public Boolean getActive() { 
        return active;
         }
}

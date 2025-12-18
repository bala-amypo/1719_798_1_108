package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DynamicPriceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Double computedPrice;
    private String appliedRuleCodes;
    private LocalDateTime computedAt;

    @PrePersist
    void onCreate() {
        computedAt = LocalDateTime.now();
    }

    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setComputedPrice(Double computedPrice) { this.computedPrice = computedPrice; }
    public void setAppliedRuleCodes(String appliedRuleCodes) { this.appliedRuleCodes = appliedRuleCodes; }
    public Double getComputedPrice() { return computedPrice; }
}

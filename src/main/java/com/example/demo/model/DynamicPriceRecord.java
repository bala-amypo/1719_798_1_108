package com.example.demo.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "dynamic_price_records")
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

    public DynamicPriceRecord() {}

    public DynamicPriceRecord(Long eventId, Double computedPrice, String appliedRuleCodes) {
        this.eventId = eventId;
        this.computedPrice = computedPrice;
        this.appliedRuleCodes = appliedRuleCodes;
    }

    public Double getComputedPrice() { return computedPrice; }
}

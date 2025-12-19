package com.example.demo.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "price_adjustment_logs")
public class PriceAdjustmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Double oldPrice;
    private Double newPrice;
    private String reason;
    private LocalDateTime changedAt;

    @PrePersist
    void onCreate() {
        changedAt = LocalDateTime.now();
    }

    public PriceAdjustmentLog() {}

    public PriceAdjustmentLog(Long eventId, Double oldPrice, Double newPrice, String reason) {
        this.eventId = eventId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.reason = reason;
    }
}

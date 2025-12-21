// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "dynamic_price_records")
// public class DynamicPriceRecord {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "event_id", nullable = false)
//     private EventRecord event;

//     private Double computedPrice;
//     private String appliedRuleCodes;

//     private LocalDateTime computedAt;

//     @PrePersist
//     public void onCreate() {
//         this.computedAt = LocalDateTime.now();
//     }

//     public DynamicPriceRecord() {
//     }

//     public DynamicPriceRecord(Long id, EventRecord event,
//                               Double computedPrice, String appliedRuleCodes) {
//         this.id = id;
//         this.event = event;
//         this.computedPrice = computedPrice;
//         this.appliedRuleCodes = appliedRuleCodes;
//     }

//     public Long getId() { return id; }
//     public EventRecord getEvent() { return event; }
//     public Double getComputedPrice() { return computedPrice; }
//     public String getAppliedRuleCodes() { return appliedRuleCodes; }
//     public LocalDateTime getComputedAt() { return computedAt; }

//     public void setId(Long id) { this.id = id; }
//     public void setEvent(EventRecord event) { this.event = event; }
//     public void setComputedPrice(Double computedPrice) { this.computedPrice = computedPrice; }
//     public void setAppliedRuleCodes(String appliedRuleCodes) { this.appliedRuleCodes = appliedRuleCodes; }
// }

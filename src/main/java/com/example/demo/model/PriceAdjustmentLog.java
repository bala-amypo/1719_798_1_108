package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_adjustment_logs")
public class PriceAdjustmentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long eventId;
    
    private Double oldPrice;
    
    @Column(nullable = false)
    private Double newPrice;
    
    private String reason;
    
    private LocalDateTime changedAt;
    
    @PrePersist
    protected void onCreate() {
        changedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    
    public Double getOldPrice() { return oldPrice; }
    public void setOldPrice(Double oldPrice) { this.oldPrice = oldPrice; }
    
    public Double getNewPrice() { return newPrice; }
    public void setNewPrice(Double newPrice) { this.newPrice = newPrice; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}













// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "price_adjustment_logs")
// public class PriceAdjustmentLog {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "event_id", nullable = false)
//     private EventRecord event;

//     private Double oldPrice;
//     private Double newPrice;
//     private String reason;

//     private LocalDateTime changedAt;

//     @PrePersist
//     public void onCreate() {
//         this.changedAt = LocalDateTime.now();
//     }

//     public PriceAdjustmentLog() {
//     }

//     public PriceAdjustmentLog(Long id, EventRecord event,
//                               Double oldPrice, Double newPrice, String reason) {
//         this.id = id;
//         this.event = event;
//         this.oldPrice = oldPrice;
//         this.newPrice = newPrice;
//         this.reason = reason;
//     }

//     public Long getId() { return id; }
//     public EventRecord getEvent() { return event; }
//     public Double getOldPrice() { return oldPrice; }
//     public Double getNewPrice() { return newPrice; }
//     public String getReason() { return reason; }
//     public LocalDateTime getChangedAt() { return changedAt; }

//     public void setId(Long id) { this.id = id; }
//     public void setEvent(EventRecord event) { this.event = event; }
//     public void setOldPrice(Double oldPrice) { this.oldPrice = oldPrice; }
//     public void setNewPrice(Double newPrice) { this.newPrice = newPrice; }
//     public void setReason(String reason) { this.reason = reason; }
// }

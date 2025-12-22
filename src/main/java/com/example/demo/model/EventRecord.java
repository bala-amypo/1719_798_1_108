package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "event_records")
public class EventRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String eventCode;

    @Column(nullable = false)
    private String eventName;

    private String venue;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private Double basePrice;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEventCode() { return eventCode; }
    public void setEventCode(String eventCode) { this.eventCode = eventCode; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}




// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDate;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "event_records")
// public class EventRecord {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     @Column(unique = true, nullable = false)
//     private String eventCode;
    
//     @Column(nullable = false)
//     private String eventName;
    
//     private String venue;
    
//     @Column(name = "event_date")
//     private LocalDate eventDate;
    
//     @Column(name = "base_price")
//     private Double basePrice;
    
//     @Column(name = "created_at")
//     private LocalDateTime createdAt;
    
//     private Boolean active;
    
//     public EventRecord() {}
    
//     public EventRecord(String eventCode, String eventName, String venue, 
//                       LocalDate eventDate, Double basePrice) {
//         this.eventCode = eventCode;
//         this.eventName = eventName;
//         this.venue = venue;
//         this.eventDate = eventDate;
//         this.basePrice = basePrice;
//         this.active = true;
//     }
    
//     // Getters and Setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }
    
//     public String getEventCode() { return eventCode; }
//     public void setEventCode(String eventCode) { this.eventCode = eventCode; }
    
//     public String getEventName() { return eventName; }
//     public void setEventName(String eventName) { this.eventName = eventName; }
    
//     public String getVenue() { return venue; }
//     public void setVenue(String venue) { this.venue = venue; }
    
//     public LocalDate getEventDate() { return eventDate; }
//     public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    
//     public Double getBasePrice() { return basePrice; }
//     public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
    
//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
//     public Boolean getActive() { return active; }
//     public void setActive(Boolean active) { this.active = active; }
    
//     @PrePersist
//     public void prePersist() {
//         this.createdAt = LocalDateTime.now();
//         if (this.active == null) {
//             this.active = true;
//         }
//     }
// }









// package com.example.demo.model;
// import jakarta.persistence.*;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;
// @Entity
// @Table(name = "event_records")
// public class EventRecord {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     @Column(unique = true)
//     private String eventCode;
//     private String eventName;
//     private String venue;
//     private LocalDate eventDate;
//     private Double basePrice;
//     private Boolean active = true;
//     private LocalDateTime createdAt;
//     @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//     private List<SeatInventoryRecord> inventories;
//     @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//     private List<DynamicPriceRecord> prices;
//     @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//     private List<PriceAdjustmentLog> adjustments;
//     @PrePersist
//     public void onCreate() {
//         this.createdAt = LocalDateTime.now();
//     }
//     public EventRecord() {
//     }
//     public EventRecord(Long id, String eventCode, String eventName,
//                        String venue, LocalDate eventDate,
//                        Double basePrice, Boolean active) {
//         this.id = id;
//         this.eventCode = eventCode;
//         this.eventName = eventName;
//         this.venue = venue;
//         this.eventDate = eventDate;
//         this.basePrice = basePrice;
//         this.active = active;
//     }
//     public Long getId() { return id; }
//     public String getEventCode() { return eventCode; }
//     public String getEventName() { return eventName; }
//     public String getVenue() { return venue; }
//     public LocalDate getEventDate() { return eventDate; }
//     public Double getBasePrice() { return basePrice; }
//     public Boolean getActive() { return active; }
//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public List<SeatInventoryRecord> getInventories() { return inventories; }
//     public List<DynamicPriceRecord> getPrices() { return prices; }
//     public List<PriceAdjustmentLog> getAdjustments() { return adjustments; }

//     public void setId(Long id) { this.id = id; }
//     public void setEventCode(String eventCode) { this.eventCode = eventCode; }
//     public void setEventName(String eventName) { this.eventName = eventName; }
//     public void setVenue(String venue) { this.venue = venue; }
//     public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
//     public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
//     public void setActive(Boolean active) { this.active = active; }
//     public void setInventories(List<SeatInventoryRecord> inventories) { this.inventories = inventories; }
//     public void setPrices(List<DynamicPriceRecord> prices) { this.prices = prices; }
//     public void setAdjustments(List<PriceAdjustmentLog> adjustments) { this.adjustments = adjustments; }
// }

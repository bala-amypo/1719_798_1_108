package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event_records")
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String eventCode;

    private String eventName;
    private String venue;
    private LocalDate eventDate;
    private Double basePrice;
    private Boolean active = true;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "eventId")
    private List<SeatInventoryRecord> inventories;

    @OneToMany(mappedBy = "eventId")
    private List<DynamicPriceRecord> prices;

    @OneToMany(mappedBy = "eventId")
    private List<PriceAdjustmentLog> adjustments;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public EventRecord() {
    }

    public EventRecord(Long id, String eventCode, String eventName, String venue,
                       LocalDate eventDate, Double basePrice, Boolean active) {
        this.id = id;
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
        this.basePrice = basePrice;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getEventCode() { return eventCode; }
    public String getEventName() { return eventName; }
    public String getVenue() { return venue; }
    public LocalDate getEventDate() { return eventDate; }
    public Double getBasePrice() { return basePrice; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setEventCode(String eventCode) { this.eventCode = eventCode; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public void setVenue(String venue) { this.venue = venue; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
    public void setActive(Boolean active) { this.active = active; }
}

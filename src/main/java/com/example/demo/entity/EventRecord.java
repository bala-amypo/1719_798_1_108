package com.example.demo.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "event_records", uniqueConstraints = @UniqueConstraint(columnNames = "eventCode"))
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventCode;
    private String eventName;
    private String venue;
    private LocalDate eventDate;
    private Double basePrice;
    private Boolean active = true;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public EventRecord() {}

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
    public Double getBasePrice() { return basePrice; }
    public Boolean getActive() { return active; }
    public LocalDate getEventDate() { return eventDate; }
    public void setActive(Boolean active) { this.active = active; }
}

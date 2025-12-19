package com.example.demo.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "seat_inventory_records")
public class SeatInventoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eventId;
    private Integer totalSeats;
    private Integer remainingSeats;
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public SeatInventoryRecord() {}

    public SeatInventoryRecord(Long id, Long eventId, Integer totalSeats, Integer remainingSeats) {
        this.id = id;
        this.eventId = eventId;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
    }

    public Long getEventId() { return eventId; }
    public Integer getRemainingSeats() { return remainingSeats; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setRemainingSeats(Integer remainingSeats) { this.remainingSeats = remainingSeats; }
}

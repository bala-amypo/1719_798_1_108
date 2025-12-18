package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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
    void updateTime() {
        updatedAt = LocalDateTime.now();
    }

    public Long getEventId() { return eventId; }
    public Integer getTotalSeats() { return totalSeats; }
    public Integer getRemainingSeats() { return remainingSeats; }
}

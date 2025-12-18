package com.example.demo.model;

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
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}

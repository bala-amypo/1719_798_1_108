package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "eventCode"))
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventCode;
    private String eventName;
    private String venue;
    private LocalDate eventDate;
    private Double basePrice;
    private Boolean active;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}

package com.example.demo.entity;
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
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
    public Long getId() { 
        return id; 
        }
    public String getEventCode() { 
        return eventCode; 
        }
    public Double getBasePrice() { 
        return basePrice;
         }
    public Boolean getActive() { 
        return active;
         }
    public void setActive(Boolean active) { 
        this.active = active; 
        }
}

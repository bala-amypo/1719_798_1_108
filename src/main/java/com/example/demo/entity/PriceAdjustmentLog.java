package com.example.demo.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class PriceAdjustmentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventId;
    private Double oldPrice;
    private Double newPrice;
    private String reason;
    private LocalDateTime changedAt;
    @PrePersist
    void onCreate() {
        changedAt = LocalDateTime.now();
    }
    public void setEventId(Long eventId) {
         this.eventId = eventId;
          }
    public void setOldPrice(Double oldPrice) { 
        this.oldPrice = oldPrice; 
        }
    public void setNewPrice(Double newPrice) {
         this.newPrice = newPrice; 
         }
    public void setReason(String reason) {
         this.reason = reason;
          }
}

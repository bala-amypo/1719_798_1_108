package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_inventory_records")
public class SeatInventoryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long eventId;
    
    @Column(nullable = false)
    private Integer totalSeats;
    
    @Column(nullable = false)
    private Integer remainingSeats;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    
    public Integer getRemainingSeats() { return remainingSeats; }
    public void setRemainingSeats(Integer remainingSeats) { this.remainingSeats = remainingSeats; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}





// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "seat_inventory_records")
// public class SeatInventoryRecord {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "event_id", nullable = false)
//     private EventRecord event;

//     private Integer totalSeats;
//     private Integer remainingSeats;
//     private LocalDateTime updatedAt;

//     @PrePersist
//     @PreUpdate
//     public void onUpdate() {
//         this.updatedAt = LocalDateTime.now();
//     }

//     public Long getId() { return id; }
//     public EventRecord getEvent() { return event; }
//     public Integer getTotalSeats() { return totalSeats; }
//     public Integer getRemainingSeats() { return remainingSeats; }
//     public LocalDateTime getUpdatedAt() { return updatedAt; }

//     public void setId(Long id) { this.id = id; }
//     public void setEvent(EventRecord event) { this.event = event; }
//     public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
//     public void setRemainingSeats(Integer remainingSeats) { this.remainingSeats = remainingSeats; }
// }


// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "seat_inventory_records")
// public class SeatInventoryRecord {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "event_id", nullable = false)
//     private EventRecord event;

//     private Integer totalSeats;
//     private Integer remainingSeats;
//     private LocalDateTime updatedAt;

//     @PrePersist
//     @PreUpdate
//     public void onUpdate() {
//         this.updatedAt = LocalDateTime.now();
//     }

//     public SeatInventoryRecord() {}

//     public SeatInventoryRecord(Long id, EventRecord event,
//                                Integer totalSeats, Integer remainingSeats) {
//         this.id = id;
//         this.event = event;
//         this.totalSeats = totalSeats;
//         this.remainingSeats = remainingSeats;
//     }

//     public Long getId() { return id; }
//     public EventRecord getEvent() { return event; }
//     public Integer getTotalSeats() { return totalSeats; }
//     public Integer getRemainingSeats() { return remainingSeats; }
//     public LocalDateTime getUpdatedAt() { return updatedAt; }

//     public void setId(Long id) { this.id = id; }
//     public void setEvent(EventRecord event) { this.event = event; }
//     public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
//     public void setRemainingSeats(Integer remainingSeats) { this.remainingSeats = remainingSeats; }
// }


// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "seat_inventory_records")
// public class SeatInventoryRecord {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "event_id", nullable = false)
//     private EventRecord event;

//     private Integer totalSeats;
//     private Integer remainingSeats;

//     private LocalDateTime updatedAt;

//     @PrePersist
//     @PreUpdate
//     public void onUpdate() {
//         this.updatedAt = LocalDateTime.now();
//     }

//     public SeatInventoryRecord() {
//     }

//     public SeatInventoryRecord(Long id, EventRecord event,
//                                Integer totalSeats, Integer remainingSeats) {
//         this.id = id;
//         this.event = event;
//         this.totalSeats = totalSeats;
//         this.remainingSeats = remainingSeats;
//     }

//     public Long getId() {
//         return id;
//     }

//     public EventRecord getEvent() {
//         return event;
//     }

//     public Long getEventId() {
//         return event != null ? event.getId() : null;
//     }

//     public Integer getTotalSeats() {
//         return totalSeats;
//     }

//     public Integer getRemainingSeats() {
//         return remainingSeats;
//     }

//     public LocalDateTime getUpdatedAt() {
//         return updatedAt;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public void setEvent(EventRecord event) {
//         this.event = event;
//     }

//     public void setTotalSeats(Integer totalSeats) {
//         this.totalSeats = totalSeats;
//     }

//     public void setRemainingSeats(Integer remainingSeats) {
//         this.remainingSeats = remainingSeats;
//     }
// }

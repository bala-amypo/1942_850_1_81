package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LifecycleEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;
    private String eventType;
    private String eventDescription;
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "performed_by")
    private User performedBy;
    @PrePersist
    public void setEventDate() {
        if (eventDate == null) {
            eventDate = LocalDateTime.now();
        }
    }
   
}

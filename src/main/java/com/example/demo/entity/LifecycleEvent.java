package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LifecycleEvent {
    @Id
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Asset getAsset() {
        return asset;
    }
    public void setAsset(Asset asset) {
        this.asset = asset;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public LocalDateTime getEventDate() {
        return eventDate;
    }
    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
    public User getPerformedBy() {
        return performedBy;
    }
    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }
    private Asset asset;
    public LifecycleEvent(Asset asset, String eventType, String eventDescription, LocalDateTime eventDate,
            User performedBy) {
        this.asset = asset;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.performedBy = performedBy;
    }
    private String eventType;
    private String eventDescription;
    private LocalDateTime eventDate;
    private User performedBy;

    public LifecycleEvent(){
        
    }
}

package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DisposalRecord {
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
    public String getDisposalMethod() {
        return disposalMethod;
    }
    public void setDisposalMethod(String disposalMethod) {
        this.disposalMethod = disposalMethod;
    }
    public LocalDate getDisposalDate() {
        return disposalDate;
    }
    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }
    public User getApprovedBy() {
        return approvedBy;
    }
    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    private Asset asset;
    public DisposalRecord(Asset asset, String disposalMethod, LocalDate disposalDate, User approvedBy, String notes,
            LocalDateTime createdAt) {
        this.asset = asset;
        this.disposalMethod = disposalMethod;
        this.disposalDate = disposalDate;
        this.approvedBy = approvedBy;
        this.notes = notes;
        this.createdAt = createdAt;
    }
    private String disposalMethod;
    private LocalDate disposalDate;
    private User approvedBy;
    private String notes;
    private LocalDateTime createdAt;

    public DisposalRecord(){

    }

}

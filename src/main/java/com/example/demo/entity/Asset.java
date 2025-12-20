package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String assetTag;
    private String assetType;
    private String model;
    private LocalDate purchaseDate;
    private String status;
    @ManyToOne
    @JoinColumn(name = "current_holder_id")
    private User currentHolder;
    private LocalDateTime createdAt;
    @PrePersist
    public void setDefaults() {
        if (status == null) {
            status = "AVAILABLE";
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}

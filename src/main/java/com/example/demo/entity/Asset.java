package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    public void prePersist() {
        if (status == null) status = "AVAILABLE";
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}

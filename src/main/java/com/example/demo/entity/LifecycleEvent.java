package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LifecycleEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset asset;

    private String eventType;
    private String eventDescription;

    private LocalDateTime eventDate;

    @ManyToOne
    private User performedBy;

    @PrePersist
    public void prePersist() {
        if (eventDate == null) eventDate = LocalDateTime.now();
    }
}

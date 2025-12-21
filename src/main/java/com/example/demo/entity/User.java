package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String department;
    private String role;
    private String password;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (role == null) role = "USER";
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}

package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id @GeneratedValue
    private Long id;
    private String fullName;
    private String email;
    private String department;
    private String role;
    private String password;
    private LocalDateTime createdAt;

    public User() {}

    public User(Long id, String fullName, String email, String department,
                String role, String password, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.role = role;
        this.password = password;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (role == null) role = "USER";
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    // getters & setters
    // (generate all)
}

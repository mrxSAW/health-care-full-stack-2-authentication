package com.example.HealthCareApp.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}

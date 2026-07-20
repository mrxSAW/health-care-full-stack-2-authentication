package com.example.HealthCareApp.DTO.Auth;

import com.example.HealthCareApp.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private int userId;
    private Role role;
}

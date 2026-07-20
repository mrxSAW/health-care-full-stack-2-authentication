package com.example.HealthCareApp.DTO.User;

import com.example.HealthCareApp.Entity.Role;
import lombok.Data;

@Data
public class UserGetDTO {

    private int id;
    private String username;
    private String email;
    private Role role;
}

package com.example.HealthCareApp.DTO.Auth;

import com.example.HealthCareApp.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "user name obligatoire ")
    private String username;


    @NotBlank(message = "email obligatoire")
    @Email(message = "email invalid ")
    private String email;

    @NotBlank(message = "pass word obligatoire")
    private String password;

    private Role role;
}

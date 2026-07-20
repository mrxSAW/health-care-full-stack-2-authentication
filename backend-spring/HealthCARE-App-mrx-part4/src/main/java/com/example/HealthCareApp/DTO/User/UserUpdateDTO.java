package com.example.HealthCareApp.DTO.User;

import com.example.HealthCareApp.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @NotBlank(message = "username obligatoire")
    private String username;

    @NotBlank(message = "email obligatoire")
    @Email(message = "email invalide")
    private String email;

    @NotNull(message = "role obligatoire")
    private Role role;
}

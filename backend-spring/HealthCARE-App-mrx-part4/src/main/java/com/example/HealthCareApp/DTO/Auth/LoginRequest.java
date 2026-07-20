package com.example.HealthCareApp.DTO.Auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {


    @NotBlank(message = "email obligatoire ")
    @Email(message = "email invalid ")
    private String email;

    @NotBlank(message = "pass word invalid ")
    private String password;

}

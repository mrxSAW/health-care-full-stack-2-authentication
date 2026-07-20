package com.example.HealthCareApp.DTO.Medcin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MedcinPostDTO {

    @NotBlank(message = "nom obligatoire")
    private String nom;

    @NotBlank(message = "specialité obligatoire ")
    private String specialite;

    @Email(message = "email obligatoire")
    private String email;

    @NotBlank(message = "telephone obligatoire")
    private String telephone;

    private Integer userId;



}

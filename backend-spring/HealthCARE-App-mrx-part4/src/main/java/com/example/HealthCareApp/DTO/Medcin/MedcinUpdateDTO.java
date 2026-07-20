package com.example.HealthCareApp.DTO.Medcin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MedcinUpdateDTO {

    @NotBlank(message = "nom obligatoire")
    private String nom;

    @NotBlank(message = "specialité obligatroire")
    private String specialite;

    @NotBlank(message = "email obligatoire ")
    private String email;

    @NotBlank(message = "telephone obligatoire")
    private String telephone;



}

package com.example.HealthCareApp.DTO.Patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PatientPostDTO {

    @NotBlank(message = "nom obligatoire")
    private String nom;

    @NotBlank(message = "prenom obligatoire")
    private String prenom;

    @Email(message = "email obligatoire")
    private String email;

    @NotBlank(message = "telephone obligatoire")
    private String telephone;

    @NotNull(message = "date de naissance obligatoire")
    @Past
    private LocalDate dateNaissance;

    private Integer userId;
}


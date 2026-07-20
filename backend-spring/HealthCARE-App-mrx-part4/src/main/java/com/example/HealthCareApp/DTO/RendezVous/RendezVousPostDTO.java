package com.example.HealthCareApp.DTO.RendezVous;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class RendezVousPostDTO {

    @NotNull(message = "date de rendez vous obligatoire")
    @Future
    private LocalDateTime dateRendezVous;

    @NotBlank(message = "statut obligatoire")
    private String statut;

    @NotNull(message = "patient Id obligatoire ")
    private int patientId;

    @NotNull(message = "medecin Id obligatoire ")
    private int medcinId;
}

package com.example.HealthCareApp.DTO.RendezVous;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RendezVousGetDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private int id;

    private LocalDateTime dateRendezVous;
    private String statut;

    private String patientNom;
    private String patientPrenom;
    private String medcinNom;


}

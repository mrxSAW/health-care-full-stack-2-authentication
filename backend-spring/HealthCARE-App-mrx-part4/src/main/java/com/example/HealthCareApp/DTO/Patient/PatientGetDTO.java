package com.example.HealthCareApp.DTO.Patient;

import com.example.HealthCareApp.Entity.RendezVous;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class PatientGetDTO  implements Serializable {

    private static final long serialVersionUID=1L;

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private LocalDate dateNaissance;

}

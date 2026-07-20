package com.example.HealthCareApp.DTO.DossierMedical;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


@Data
public class DossierMedicalGetDTO implements Serializable {

    private static final long serialVersionUID=1L;
    private int id;
    private String diagnostic;
    private String observation;
    private LocalDate dateCreation;

    private String patientNom;
    private String patientPrenom;
}

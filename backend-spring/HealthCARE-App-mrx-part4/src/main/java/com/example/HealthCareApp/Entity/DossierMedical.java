package com.example.HealthCareApp.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class DossierMedical {

  @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

  private String diagnostic;
  private String observation;
  private LocalDate dateCreation;


@OneToOne
@JoinColumn(name = "patient_id")
    private Patient patient;


}

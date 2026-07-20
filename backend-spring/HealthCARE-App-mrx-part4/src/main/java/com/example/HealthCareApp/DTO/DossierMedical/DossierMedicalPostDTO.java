package com.example.HealthCareApp.DTO.DossierMedical;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DossierMedicalPostDTO {

    @NotBlank(message = "diagnostic obligatoire")
    private String diagnostic;

    @NotBlank(message = "observation oblegatoire")
    private String observation;

    @NotNull(message = "date de creation obligatoire ")
    private LocalDate dateCreation;

    @NotNull(message = "id patient obligatoire ")
    private int patientId;
}

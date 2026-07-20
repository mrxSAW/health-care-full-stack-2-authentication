package com.example.HealthCareApp.DTO.DossierMedical;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DossierMedicalUpdateDTO {

    @NotBlank(message = "diagnostic obligatoire")
    private String diagnostic;

    @NotBlank(message = "observation obligatoire ")
    private String observation;


}

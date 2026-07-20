package com.example.HealthCareApp.Mapper;

import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalGetDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalPostDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalUpdateDTO;
import com.example.HealthCareApp.Entity.DossierMedical;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DossierMedicalMapper {

    DossierMedical toEntity(DossierMedicalPostDTO dto);

    @Mapping(source = "patient.nom",target = "patientNom")
    @Mapping(source = "patient.prenom",target = "patientPrenom")
    DossierMedicalGetDTO toGetDTO(DossierMedical dossierMedical);

    void updateDossierMedicalFromDTO(DossierMedicalUpdateDTO dto, @MappingTarget DossierMedical dossierMedical);
}

package com.example.HealthCareApp.Mapper;


import com.example.HealthCareApp.DTO.RendezVous.RendezVousGetDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousPostDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousUpdateDTO;
import com.example.HealthCareApp.Entity.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {

    RendezVous toEntity(RendezVousPostDTO dto);


    @Mapping(source = "patient.nom",target="patientNom")
    @Mapping(source = "patient.prenom",target = "patientPrenom")
    @Mapping(source = "medcin.nom",target = "medcinNom")
    RendezVousGetDTO toGetDTO(RendezVous rendezVous);

    void updateRendezVousFromDTO(RendezVousUpdateDTO dto, @MappingTarget RendezVous rendezVous);
}
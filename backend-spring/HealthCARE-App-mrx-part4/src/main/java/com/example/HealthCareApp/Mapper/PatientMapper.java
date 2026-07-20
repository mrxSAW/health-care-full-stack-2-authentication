package com.example.HealthCareApp.Mapper;

import com.example.HealthCareApp.DTO.Patient.PatientGetDTO;
import com.example.HealthCareApp.DTO.Patient.PatientPostDTO;
import com.example.HealthCareApp.DTO.Patient.PatientUpdateDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousGetDTO;
import com.example.HealthCareApp.Entity.Patient;
import com.example.HealthCareApp.Entity.RendezVous;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(PatientPostDTO dto);

    PatientGetDTO toGetDTO(Patient patient);




    void updatePatientFromDTO(PatientUpdateDTO dto, @MappingTarget Patient patient);


}


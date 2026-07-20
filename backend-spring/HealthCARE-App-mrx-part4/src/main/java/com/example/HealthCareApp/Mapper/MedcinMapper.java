package com.example.HealthCareApp.Mapper;

import com.example.HealthCareApp.DTO.Medcin.MedcinGetDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinPostDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinUpdateDTO;
import com.example.HealthCareApp.Entity.Medcin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedcinMapper {

    Medcin toEntity(MedcinPostDTO dto);

    MedcinGetDTO toGetDTO(Medcin medcin);

    void updateMedcinFromDTO(MedcinUpdateDTO dto, @MappingTarget Medcin medcin);
}
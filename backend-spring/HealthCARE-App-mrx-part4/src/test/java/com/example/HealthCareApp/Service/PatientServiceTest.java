package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.Patient.PatientGetDTO;
import com.example.HealthCareApp.DTO.Patient.PatientPostDTO;
import com.example.HealthCareApp.DTO.Patient.PatientUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class PatientServiceTest {


    @Autowired
    private PatientService service;


    @Test
    void save() {

        PatientPostDTO dto = new PatientPostDTO();
        dto.setNom("Ali");
        dto.setPrenom("Ahmed");
        dto.setEmail("ali@test.com");
        dto.setTelephone("0600000000");
        dto.setDateNaissance(LocalDate.of(2000, 1, 1));

        PatientGetDTO result = service.save(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Ali", result.getNom());
    }







    @Test
    void getAll() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<PatientGetDTO> list = service.getAll(pageable);

        assertNotNull(list);
    }


    @Test
    void getById() {

        PatientPostDTO dto = new PatientPostDTO();
        dto.setNom("Test");
        dto.setPrenom("User");

        PatientGetDTO saved = service.save(dto);

        PatientGetDTO found = service.getById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
    }


    @Test
    void update() {

        PatientPostDTO dto = new PatientPostDTO();
        dto.setNom("Old");

        PatientGetDTO saved = service.save(dto);

        PatientUpdateDTO updateDTO = new PatientUpdateDTO();
        updateDTO.setNom("New Name");

        PatientGetDTO updated = service.update(saved.getId(), updateDTO);

        assertEquals("New Name", updated.getNom());
    }


    @Test
    void delete() {

        PatientPostDTO dto = new PatientPostDTO();
        dto.setNom("ToDelete");

        PatientGetDTO saved = service.save(dto);

        service.delete(saved.getId());

        PatientGetDTO result = service.getById(saved.getId());

        assertNull(result);
    }

}
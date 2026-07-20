package com.example.HealthCareApp.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalGetDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalPostDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalUpdateDTO;
import com.example.HealthCareApp.DTO.Patient.PatientGetDTO;
import com.example.HealthCareApp.DTO.Patient.PatientPostDTO;
import com.example.HealthCareApp.Entity.DossierMedical;
import com.example.HealthCareApp.Entity.Patient;
import com.example.HealthCareApp.Repository.DossierMedicalRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DossierMedicalServiceTest {

    @Autowired
    private DossierMedicalService service;

    @Autowired
    private PatientService patientService;
    @Autowired
    private DossierMedicalRepository repo;


    @Test
    void save() {


        PatientPostDTO p = new PatientPostDTO();
        p.setNom("Ali");
        p.setPrenom("Test");

        PatientGetDTO savedPatient = patientService.save(p);


        DossierMedicalPostDTO dto = new DossierMedicalPostDTO();
        dto.setDiagnostic("Grippe");
        dto.setObservation("Repos");
        dto.setDateCreation(LocalDate.now());
        dto.setPatientId(savedPatient.getId());

        DossierMedicalGetDTO result = service.save(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Grippe", result.getDiagnostic());
    }


    @Test
    void getAll() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<DossierMedicalGetDTO> list = service.getAll(pageable);

        assertNotNull(list);
    }


    @Test
    void getById() {

        PatientPostDTO p = new PatientPostDTO();
        p.setNom("User");

        PatientGetDTO patient = patientService.save(p);

        DossierMedicalPostDTO dto = new DossierMedicalPostDTO();
        dto.setDiagnostic("Test");
        dto.setDateCreation(LocalDate.now());
        dto.setPatientId(patient.getId());

        DossierMedicalGetDTO saved = service.save(dto);

        DossierMedicalGetDTO found = service.getById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
    }


    @Test
    void update() {

        PatientPostDTO p = new PatientPostDTO();
        p.setNom("Update");

        PatientGetDTO patient = patientService.save(p);

        DossierMedicalPostDTO dto = new DossierMedicalPostDTO();
        dto.setDiagnostic("Old");
        dto.setDateCreation(LocalDate.now());
        dto.setPatientId(patient.getId());

        DossierMedicalGetDTO saved = service.save(dto);

        DossierMedicalUpdateDTO updateDTO = new DossierMedicalUpdateDTO();
        updateDTO.setDiagnostic("New Diagnostic");
        updateDTO.setObservation("Updated");

        DossierMedicalGetDTO updated = service.update(saved.getId(), updateDTO);

        assertEquals("New Diagnostic", updated.getDiagnostic());
    }
    @Test
    void delete() {
        PatientPostDTO p = new PatientPostDTO();
        p.setNom("Delete");
        p.setPrenom("Test");
        p.setEmail("delete@test.com");
        p.setTelephone("0600000000");
        p.setDateNaissance(LocalDate.of(2000, 1, 1));

        PatientGetDTO patient = patientService.save(p);

        DossierMedicalPostDTO dto = new DossierMedicalPostDTO();
        dto.setDiagnostic("ToDelete");
        dto.setObservation("Observation");
        dto.setDateCreation(LocalDate.now());
        dto.setPatientId(patient.getId());

        DossierMedicalGetDTO saved = service.save(dto);

        service.delete(saved.getId());

        DossierMedicalGetDTO result = service.getById(saved.getId());

        assertNull(result);
    }
}
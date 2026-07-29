package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.PageResponseDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinGetDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinPostDTO;
import com.example.HealthCareApp.DTO.Patient.PatientGetDTO;
import com.example.HealthCareApp.DTO.Patient.PatientPostDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousGetDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousPostDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RendezVousServiceTest {

    @Autowired
    private RendezVousService service;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedcinService medcinService;

    @Test
    void save() {
        PatientPostDTO p = new PatientPostDTO();
        p.setNom("Ali");
        PatientGetDTO patient = patientService.save(p);

        MedcinPostDTO m = new MedcinPostDTO();
        m.setNom("Dr Ahmed");
        m.setSpecialite("Cardio");
        MedcinGetDTO medcin = medcinService.save(m);

        RendezVousPostDTO dto = new RendezVousPostDTO();
        dto.setDateRendezVous(LocalDateTime.now());
        dto.setStatut("EN_ATTENTE");
        dto.setPatientId(patient.getId());
        dto.setMedcinId(medcin.getId());

        RendezVousGetDTO result = service.save(dto);

        assertNotNull(result);
        assertTrue(result.getId() > 0);
    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 10);

        PageResponseDTO<RendezVousGetDTO> list = service.getAll(pageable);

        assertNotNull(list);
        assertNotNull(list.getContent());
    }

    @Test
    void update() {
        PatientGetDTO patient = patientService.save(new PatientPostDTO());
        MedcinGetDTO medcin = medcinService.save(new MedcinPostDTO());

        RendezVousPostDTO dto = new RendezVousPostDTO();
        dto.setDateRendezVous(LocalDateTime.now());
        dto.setStatut("OLD");
        dto.setPatientId(patient.getId());
        dto.setMedcinId(medcin.getId());

        RendezVousGetDTO saved = service.save(dto);

        RendezVousUpdateDTO updateDTO = new RendezVousUpdateDTO();
        updateDTO.setDateRendezVous(LocalDateTime.now());
        updateDTO.setStatut("UPDATED");
        updateDTO.setPatientId(patient.getId());
        updateDTO.setMedcinId(medcin.getId());

        RendezVousGetDTO updated = service.update(saved.getId(), updateDTO);

        assertEquals("UPDATED", updated.getStatut());
    }

    @Test
    void delete() {
        PatientGetDTO patient = patientService.save(new PatientPostDTO());
        MedcinGetDTO medcin = medcinService.save(new MedcinPostDTO());

        RendezVousPostDTO dto = new RendezVousPostDTO();
        dto.setDateRendezVous(LocalDateTime.now());
        dto.setStatut("DELETE");
        dto.setPatientId(patient.getId());
        dto.setMedcinId(medcin.getId());

        RendezVousGetDTO saved = service.save(dto);

        service.delete(saved.getId());

        Pageable pageable = PageRequest.of(0, 10);
        PageResponseDTO<RendezVousGetDTO> list = service.getAll(pageable);

        boolean exists = false;

        for (RendezVousGetDTO r : list.getContent()) {
            if (r.getId() == saved.getId()) {
                exists = true;
            }
        }

        assertFalse(exists);
    }

    @Test
    void findByPatient() {
        PatientPostDTO p = new PatientPostDTO();
        p.setNom("PatientSearch");
        PatientGetDTO patient = patientService.save(p);

        MedcinGetDTO medcin = medcinService.save(new MedcinPostDTO());

        RendezVousPostDTO dto = new RendezVousPostDTO();
        dto.setDateRendezVous(LocalDateTime.now());
        dto.setStatut("TEST");
        dto.setPatientId(patient.getId());
        dto.setMedcinId(medcin.getId());

        service.save(dto);

        Pageable pageable = PageRequest.of(0, 10);

        PageResponseDTO<RendezVousGetDTO> result = service.findByPatient(patient.getId(), pageable);

        assertNotNull(result);
        assertNotNull(result.getContent());
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    void findByMedcin() {
        PatientGetDTO patient = patientService.save(new PatientPostDTO());

        MedcinPostDTO m = new MedcinPostDTO();
        m.setNom("MedcinSearch");
        MedcinGetDTO medcin = medcinService.save(m);

        RendezVousPostDTO dto = new RendezVousPostDTO();
        dto.setDateRendezVous(LocalDateTime.now());
        dto.setStatut("TEST");
        dto.setPatientId(patient.getId());
        dto.setMedcinId(medcin.getId());

        service.save(dto);

        Pageable pageable = PageRequest.of(0, 10);

        PageResponseDTO<RendezVousGetDTO> result = service.findByMedcin(medcin.getId(), pageable);

        assertNotNull(result);
        assertNotNull(result.getContent());
        assertFalse(result.getContent().isEmpty());
    }
}
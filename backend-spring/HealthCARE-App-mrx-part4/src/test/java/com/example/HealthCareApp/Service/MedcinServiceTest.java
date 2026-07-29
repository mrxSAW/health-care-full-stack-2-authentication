package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.PageResponseDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinGetDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinPostDTO;
import com.example.HealthCareApp.DTO.Medcin.MedcinUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MedcinServiceTest {

    @Autowired
    private MedcinService service;

    @Test
    void save() {
        MedcinPostDTO dto = new MedcinPostDTO();
        dto.setNom("Dr Ali");
        dto.setSpecialite("Cardiologie");
        dto.setEmail("ali@test.com");
        dto.setTelephone("0600000000");

        MedcinGetDTO result = service.save(dto);

        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertEquals("Dr Ali", result.getNom());
        assertEquals("Cardiologie", result.getSpecialite());
    }

    @Test
    void getAll() {
        MedcinPostDTO dto = new MedcinPostDTO();
        dto.setNom("Dr Test");
        dto.setSpecialite("Dermatologie");

        service.save(dto);

        Pageable pageable = PageRequest.of(0, 10);

        PageResponseDTO<MedcinGetDTO> list = service.getAll(pageable);

        assertNotNull(list);
        assertNotNull(list.getContent());
        assertFalse(list.getContent().isEmpty());
    }

    @Test
    void getById() {
        MedcinPostDTO dto = new MedcinPostDTO();
        dto.setNom("Dr Samir");
        dto.setSpecialite("ORL");

        MedcinGetDTO saved = service.save(dto);
        MedcinGetDTO found = service.getById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals("Dr Samir", found.getNom());
    }

    @Test
    void update() {
        MedcinPostDTO dto = new MedcinPostDTO();
        dto.setNom("Dr Old");
        dto.setSpecialite("Generaliste");

        MedcinGetDTO saved = service.save(dto);

        MedcinUpdateDTO updateDTO = new MedcinUpdateDTO();
        updateDTO.setNom("Dr New");
        updateDTO.setSpecialite("Neurologie");
        updateDTO.setEmail("new@test.com");
        updateDTO.setTelephone("0611111111");

        MedcinGetDTO updated = service.update(saved.getId(), updateDTO);

        assertNotNull(updated);
        assertEquals("Dr New", updated.getNom());
        assertEquals("Neurologie", updated.getSpecialite());
    }

    @Test
    void delete() {
        MedcinPostDTO dto = new MedcinPostDTO();
        dto.setNom("Dr Delete");
        dto.setSpecialite("Radio");

        MedcinGetDTO saved = service.save(dto);

        service.delete(saved.getId());

        MedcinGetDTO result = service.getById(saved.getId());

        assertNull(result);
    }
}
package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.PageResponseDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalGetDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalPostDTO;
import com.example.HealthCareApp.DTO.DossierMedical.DossierMedicalUpdateDTO;
import com.example.HealthCareApp.Entity.DossierMedical;
import com.example.HealthCareApp.Entity.Patient;
import com.example.HealthCareApp.Mapper.DossierMedicalMapper;
import com.example.HealthCareApp.Repository.DossierMedicalRepository;
import com.example.HealthCareApp.Repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DossierMedicalService {

    private final DossierMedicalRepository repo;
    private final PatientRepository patientRepo;
    private final DossierMedicalMapper mapper;

    @CacheEvict(value = {"dossiersMedicaux", "dossierMedical", "dossierMedicalPatient"}, allEntries = true)
    public DossierMedicalGetDTO save(DossierMedicalPostDTO dto) {
        DossierMedical dossier = mapper.toEntity(dto);

        Patient patient = patientRepo.findById(dto.getPatientId()).orElse(null);
        dossier.setPatient(patient);

        DossierMedical saved = repo.save(dossier);
        return mapper.toGetDTO(saved);
    }

    @Cacheable(value = "dossierMedicalPatient", key = "#email")
    public DossierMedicalGetDTO getMyDossier(String email) {
        DossierMedical dossier = repo.findByPatientUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Dossier médical introuvable pour cet utilisateur"));

        return mapper.toGetDTO(dossier);
    }

    @Cacheable(value = "dossiersMedicaux", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public PageResponseDTO<DossierMedicalGetDTO> getAll(Pageable pageable) {
        Page<DossierMedicalGetDTO> page = repo.findAll(pageable)
                .map(dossierMedical -> mapper.toGetDTO(dossierMedical));

        return PageResponseDTO.from(page);
    }

    @Cacheable(value = "dossierMedical", key = "#id")
    public DossierMedicalGetDTO getById(int id) {
        DossierMedical dossier = repo.findById(id).orElse(null);

        if (dossier == null) {
            return null;
        }

        return mapper.toGetDTO(dossier);
    }

    @CacheEvict(value = {"dossiersMedicaux", "dossierMedical", "dossierMedicalPatient"}, allEntries = true)
    public DossierMedicalGetDTO update(int id, DossierMedicalUpdateDTO dto) {
        DossierMedical dossier = repo.findById(id).orElse(null);

        if (dossier == null) {
            return null;
        }

        mapper.updateDossierMedicalFromDTO(dto, dossier);

        DossierMedical updated = repo.save(dossier);
        return mapper.toGetDTO(updated);
    }

    @CacheEvict(value = {"dossiersMedicaux", "dossierMedical", "dossierMedicalPatient"}, allEntries = true)
    public void delete(int id) {
        DossierMedical dossier = repo.findById(id).orElse(null);

        if (dossier != null) {
            Patient patient = dossier.getPatient();

            if (patient != null) {
                patient.setDossierMedical(null);
                dossier.setPatient(null);
            }

            repo.delete(dossier);
            repo.flush();
        }
    }



    @Cacheable(value = "dossierMedicalPatientId", key = "#patientId")
    public DossierMedicalGetDTO getByPatientId(int patientId) {
        DossierMedical dossier = repo.findByPatientId(patientId).orElseThrow(() -> new RuntimeException("Aucun dossier médical trouvé pour ce patient"));

        return mapper.toGetDTO(dossier);
    }







}



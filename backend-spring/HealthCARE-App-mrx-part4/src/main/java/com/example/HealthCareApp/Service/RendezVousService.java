package com.example.HealthCareApp.Service;

import com.example.HealthCareApp.DTO.RendezVous.RendezVousGetDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousPostDTO;
import com.example.HealthCareApp.DTO.RendezVous.RendezVousUpdateDTO;
import com.example.HealthCareApp.Entity.Medcin;
import com.example.HealthCareApp.Entity.Patient;
import com.example.HealthCareApp.Entity.RendezVous;
import com.example.HealthCareApp.Mapper.RendezVousMapper;
import com.example.HealthCareApp.Repository.MedcinRepository;
import com.example.HealthCareApp.Repository.PatientRepository;
import com.example.HealthCareApp.Repository.RendezVousRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RendezVousService {

    private final RendezVousRepository repo;
    private final PatientRepository patientRepo;
    private final MedcinRepository medcinRepo;
    private final RendezVousMapper mapper;

    public RendezVousService(RendezVousRepository repo, PatientRepository patientRepo, MedcinRepository medcinRepo, RendezVousMapper mapper) {
        this.repo = repo;
        this.patientRepo = patientRepo;
        this.medcinRepo = medcinRepo;
        this.mapper = mapper;
    }

    @CacheEvict(value = {"rendezVous", "rendezVousParPatient", "rendezVousParMedcin"}, allEntries = true)
    public RendezVousGetDTO save(RendezVousPostDTO dto) {
        RendezVous rendezVous = mapper.toEntity(dto);

        Patient patient = patientRepo.findById(dto.getPatientId()).orElse(null);
        Medcin medcin = medcinRepo.findById(dto.getMedcinId()).orElse(null);

        rendezVous.setPatient(patient);
        rendezVous.setMedcin(medcin);

        RendezVous saved = repo.save(rendezVous);
        return mapper.toGetDTO(saved);
    }

    @Cacheable(value = "rendezVousParPatient", key = "#email + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<RendezVousGetDTO> findMyPatientRendezVous(String email, Pageable pageable) {
        return repo.findByPatientUserEmail(email, pageable)
                .map(mapper::toGetDTO);
    }

    @Cacheable(value = "rendezVousParMedcin", key = "#email + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<RendezVousGetDTO> findMyMedcinRendezVous(String email, Pageable pageable) {
        return repo.findByMedcinUserEmail(email, pageable)
                .map(mapper::toGetDTO);
    }

    @Cacheable( value = "rendezVous",key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<RendezVousGetDTO> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(rendezVous -> mapper.toGetDTO(rendezVous));
    }

    public Page<RendezVousGetDTO> searchByStatut(String statut, Pageable pageable) {
        return repo.findByStatutContainingIgnoreCase(statut, pageable)
                .map(rendezVous -> mapper.toGetDTO(rendezVous));
    }

    @Cacheable(value = "rendezVousPatientId",
            key = "#patientId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<RendezVousGetDTO> findByPatient(int patientId, Pageable pageable) {
        return repo.findByPatientId(patientId, pageable)
                .map(rendezVous -> mapper.toGetDTO(rendezVous));
    }

    @Cacheable(value = "rendezVousMedcinId",
            key = "#medcinId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<RendezVousGetDTO> findByMedcin(int medcinId, Pageable pageable) {
        return repo.findByMedcinId(medcinId, pageable)
                .map(rendezVous -> mapper.toGetDTO(rendezVous));
    }

    @CacheEvict(
            value = {"rendezVous", "rendezVousParPatient", "rendezVousParMedcin", "rendezVousPatientId", "rendezVousMedcinId"},
            allEntries = true)
    public RendezVousGetDTO update(int id, RendezVousUpdateDTO dto) {
        RendezVous rendezVous = repo.findById(id).orElse(null);

        if (rendezVous == null) {
            return null;
        }

        mapper.updateRendezVousFromDTO(dto, rendezVous);

        Patient patient = patientRepo.findById(dto.getPatientId()).orElse(null);
        Medcin medcin = medcinRepo.findById(dto.getMedcinId()).orElse(null);

        rendezVous.setPatient(patient);
        rendezVous.setMedcin(medcin);

        RendezVous updated = repo.save(rendezVous);
        return mapper.toGetDTO(updated);
    }

    @CacheEvict( value = {"rendezVous", "rendezVousParPatient", "rendezVousParMedcin",
                    "rendezVousPatientId", "rendezVousMedcinId"}, allEntries = true)
    public void delete(int id) {
        repo.deleteById(id);
    }
}